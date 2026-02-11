const mongoose = require('mongoose');
mongoose.Promise = global.Promise;
const slug = require('slugs');

const storeSchema = new mongoose.Schema({
    name: {
        type: String,
        trim: true,
        required: 'Please enter a store name!'
    },
    slug: String,
    description: {
        type: String,
        trim: true
    },
    address: {
        type: String,
        trim: true
    },
    location: {
        latitud: {
            type: Number
        },
        longitud: {
            type: Number
        }
    },
    tags: [String],
    created: {
        type: Date,
        default: Date.now
    },
    photo: String,
    author: {
        type: mongoose.Schema.ObjectId,
        ref: 'User',
        required: 'You must supply an author'
    },
    averageRating: {
        type: Number,
        default: 0,
        min: 0,
        max: 5
    }
});

// PRE-SAVE HOOK

storeSchema.pre('save', async function(next) {
    if (!this.isModified('name')) {
        next(); // skip it
        return; // stop this function from running
    }
    this.slug = slug(this.name);
    // find other stores that have a slug of store, store-1, store-2
    const slugRegEx = new RegExp(`^(${this.slug})((-[0-9]*$)?)$`, 'i');
    const storesWithSlug = await this.constructor.find({ slug: slugRegEx });
    if (storesWithSlug.length) {
        this.slug = `${this.slug}-${storesWithSlug.length + 1}`;
    }
    next();
});

// INDEXES a los atributos de la base de datos
storeSchema.index({
    name: 'text',
    description: 'text'
});

storeSchema.statics.getTagsList = function() {
    return this.aggregate([
        { $unwind: '$tags' },
        { $group: {_id: '$tags', count: { $sum: 1 } } },
        { $sort: { count: -1 } }
    ]);
};

storeSchema.virtual('reviews', {
    ref: 'Review',
    localField: '_id',
    foreignField: 'store'
});

function autopopulate(next) {
    this.populate('reviews');
    next();
}

storeSchema.pre('find', autopopulate);
storeSchema.pre('findOne', autopopulate);

storeSchema.statics.getTopStores = function() {
    return this.aggregate([
        {
            $lookup: {
                from: 'reviews',
                localField: '_id',
                foreignField: 'store',
                as: 'reviews'
            }
        },
        {
            $match: {
                'reviews.1': { $exists: true } // Only include stores with at least 2 reviews
            }
        },
        {
            $addFields: {
                averageRating: { $avg: '$reviews.rating' }
            }
        },
        {
            $sort: {
                averageRating: -1 // Sort by highest average rating
            }
        },
        {
            $limit: 10 // Limit to the top 10 stores
        }
    ]);
};

module.exports = mongoose.model('Store', storeSchema);
