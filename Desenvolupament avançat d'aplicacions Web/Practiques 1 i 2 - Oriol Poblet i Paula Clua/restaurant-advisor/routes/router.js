const express = require('express');
const router = express.Router();

const storeController = require('../controllers/storeController');
const userController = require('../controllers/userController');
const authController = require('../controllers/authController');
const { catchErrors } = require('../handlers/errorHandlers');
const reviewController = require('../controllers/reviewController');

router.get('/index/', storeController.homePage);

router.get('/storevar/', (req, res) => {
    req.session.testVar = 1;
    res.send('variable stored');
});

// SHOW a certain STORE
router.get('/store/:slug', catchErrors(storeController.getStoreBySlug));

// API REST
router.get('/api/v1/search', catchErrors(storeController.searchStores));

// SHOW ALL TAGS
router.get('/tags', catchErrors(storeController.getStoresByTag));

// SHOW a certain TAG
router.get('/tags/:tag', catchErrors(storeController.getStoresByTag));

router.get('/stores', catchErrors(storeController.getStores));

// edit showing the form
router.get('/stores/:id/edit', catchErrors(storeController.editStore));

// edit receiving the updated data
router.post('/add/:id',
    storeController.verify,
    catchErrors(storeController.upload),
    catchErrors(storeController.updateStore)
);

// 1st step SIGN-UP a USER -> show the form
router.get('/register', userController.registerForm);

// 2nd step SIGN-UP a USER -> validate, register, login
router.post('/register',
    userController.validationRules(),
    userController.validationCustomRules(),
    userController.validateRegister,
    userController.register,
    authController.login
);

// 1st step ADD STORE -> show the form
router.get('/add/',
    authController.isLoggedIn,
    storeController.addStore
);

// 2nd step ADD STORE -> receive the data
router.post('/add/',
    authController.isLoggedIn,
    storeController.verify,
    catchErrors(storeController.upload),
    catchErrors(storeController.createStore)
);



// LOG OUT
router.get('/logout', authController.logout);

//1st step LOG IN -> show the form
router.get('/login', authController.loginForm);

//2nd step LOG IN -> do the login
router.post('/login', authController.login);

// SHOW ACCOUNT
router.get('/account',
    authController.isLoggedIn,
    userController.account
);

router.post('/account',
    authController.isLoggedIn,
    catchErrors(userController.updateAccount)
);

router.post('/reviews/:id',
    authController.isLoggedIn,
    catchErrors(reviewController.addReview)
);

router.get('/top', catchErrors(storeController.getTopStores));

// Recibimos la accion de olvidar cuenta
router.post('/account/forgot', catchErrors(authController.forgot));

// Primer paso para resetear el password, mostramos el formulario
router.get('/account/reset/:token', catchErrors(authController.reset));

// Segundo paso, cambiar la contraseña
router.post('/account/reset/:token',
    authController.validationCustomRules(),
    authController.validatePassUpdate,
    catchErrors(authController.updatePassword)
);

router.get('/stores', catchErrors(storeController.getStores));

router.get('/stores/page/:page', catchErrors(storeController.getStores));

// Añadimos la ruta que apunta a getMap para mostrar el mapa con todos los restaurantes
router.get('/storesMap', storeController.getMap);

module.exports = router;