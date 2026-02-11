const multer = require('multer');
const { Jimp } = require('jimp');
const uuid = require('uuid');
const mongoose = require('mongoose');
const Store = mongoose.model('Store');
const axios = require('axios');
const Review = mongoose.model('Review');

// Multer options
const multerOptions = {
    storage: multer.memoryStorage(),
    fileFilter: function(req, file, next) {
        const isPhoto = file.mimetype.startsWith('image/');
        if (isPhoto) {
            next(null, true);
        } else {
            next({ message: 'That filetype isn\'t allowed!' }, false);
        }
    }
};

exports.verify = multer(multerOptions).single('photo');

exports.upload = async (req, res, next) => {
    if (!req.file) {
        next();
        return;
    }
    console.log(req.body);
    console.log(req.file);

    const extension = req.file.mimetype.split('/')[1];
    req.body.photo = `${uuid.v4()}.${extension}`;

    const photo = await Jimp.read(req.file.buffer);
    photo.resize({ w: 800, h: Jimp.AUTO});
    await photo.write(`./public/uploads/${req.body.photo}`);
    next();
};

exports.homePage = (req, res) => {
    req.flash('error', `hola <strong>que</strong> tal`);
    req.flash('info', `hola`);
    req.flash('warning', `hola`);
    req.flash('success', `hola`);
    res.render('extendingLayout');  
};

exports.addStore = (req, res) => {
    res.render('editStore', { title: 'Add Store' });
};

exports.createStore = async (req, res) => {
    req.body.author = req.user._id;

    // Realizar la geocodificación con Nominatim usando la dirección proporcionada
    const address = req.body.address;
    if (address) {
        const geoResponse = await axios.get('https://nominatim.openstreetmap.org/search', {
            params: {
                q: address,
                format: 'json',
                limit: 1
            },
            headers: { 'User-Agent': 'RestaurantAdvisor' }
        });

        if (geoResponse.data.length > 0) {
            const location = geoResponse.data[0];
            req.body.location = {
                latitud: parseFloat(location.lat),
                longitud: parseFloat(location.lon)
            };
        } else {
            req.flash('error', 'No se encontraron coordenadas para esta dirección.');
        }
    }

    const store = new Store(req.body);
    const savedStore = await store.save();
    req.flash('success', `Successfully created ${store.name}.`);
    res.redirect(`/store/${savedStore.slug}`);
};



exports.getStoreBySlug = async (req, res, next) => {
    const store = await Store.findOne({ slug: req.params.slug }).populate('reviews');
    console.log('Average Rating:', store.averageRating); // Verifica el valor aquí
    if (!store) {
        next();
        return;
    }
    res.render('store', { title: `Store ${store.name}`, store });
};


// añadimos la funcion de la API REST para mostrar resultados search
exports.searchStores = async (req, res) => {
    const stores = await Store.find({
        $text: {
            $search: req.query.q
        }
    }, {
        score: { $meta: 'textScore' }
    }).sort({
        score: { $meta: 'textScore' }
    }).limit(5); // limitamos a 5 resultados

    res.json({ stores, length: stores.length });
};

exports.getStoresByTag = async (req, res) => {
    const tag = req.params.tag;
    const tagQuery = tag || { $exists: true};

    //Promise1: aggregate
    const tagsPromise = Store.getTagsList();

    const storesPromise = Store.find({ tags: tagQuery });

    const [tags, stores] = await Promise.all([tagsPromise, storesPromise]);

    res.render('tags', { title: 'Tags', tags: tags, stores: stores, tag: tag});
};

exports.getStores = async (req, res) => {
    const page = req.params.page || 1;
    const limit = 4; // items in each page
    const skip = (page * limit) - limit;

    const storesPromise = Store
        .find() // look for ALL
        .skip(skip) // Skip items of former pages
        .limit(limit) // Take the desired number of items
        .sort({ created: 'desc' }); // sort them

    const countPromise = Store.countDocuments();
    const [stores, count] = await Promise.all([storesPromise, countPromise]);

    const pages = Math.ceil(count / limit);
    
    if (!stores.length && skip) {
        req.flash('info', `You asked for page ${page}. But that does not exist. So I put you on page ${pages}`);
        res.redirect(`/stores/page/${pages}`);
        return;
    }

    res.render('stores', {
        title: 'Stores',
        stores: stores,
        page: page,
        pages: pages,
        count: count
    });
};


const confirmOwner = (store, user) => {
    if (!store.author.equals(user._id)) {
        throw Error('You must own a store in order to edit it!');
    }
};

exports.editStore = async (req, res) => {
    const store = await Store.findOne({ _id: req.params.id });
    confirmOwner(store, req.user);
    res.render('editStore', { title: `Edit ${store.name}`, store: store});
};

exports.updateStore = async (req, res) => {
    // find and update the store
    const store = await Store.findOneAndUpdate({ _id: req.params.id }, req.body, {
        new: true, // return new store instead of old one
        runValidators: true
    }).exec();
    req.flash('success', `Successfully updated <strong>${store.name}</strong>. 
    <a href="/store/${store.slug}">View store</a>`);
    res.redirect(`/stores/${store._id}/edit`);
};

exports.getTopStores = async (req, res) => {
    // Top Global
    const globalStores = await Store.getTopStores();

    // Top Personal (si el usuario está autenticado)
    let personalStores = [];
    if (req.user) {
        const reviews = await Review.aggregate([
            { $match: { author: new mongoose.Types.ObjectId(req.user._id) } },
            {
                $group: {
                    _id: '$store',
                    averageRating: { $avg: '$rating' },
                    reviewCount: { $sum: 1 }
                }
            },
            { $sort: { averageRating: -1 } },
            { $limit: 10 }
        ]);

        personalStores = await Store.find({
            _id: { $in: reviews.map((r) => r._id) }
        }).lean();

        personalStores = personalStores.map((store) => {
            const reviewData = reviews.find((r) => r._id.equals(store._id));
            return {
                ...store,
                userAverageRating: reviewData.averageRating.toFixed(1),
                userReviewCount: reviewData.reviewCount
            };
        });
    }

    res.render('topStores', { 
        stores: globalStores, 
        personalStores, 
        title: 'Top Stores' 
    });
};



// Método que obtiene todos restaurantes junto con sus ubicaciones y datos
exports.getMap = async (req, res) => {
    const stores = await Store.find({}, 'name location averageRating'); // Solo obtenemos nombre, ubi y nota
    res.render('storesMap', { title: 'Map', stores });
};
