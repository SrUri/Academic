const passport = require('passport');
const crypto = require('crypto');
const mongoose = require('mongoose');
const User = mongoose.model('User');
const mail = require('../handlers/mail');
const { body, validationResult } = require('express-validator');

exports.login = (req, res) => {
    passport.authenticate('local', 
        {
            failureRedirect: '/login',
            failureFlash: 'Failed Login!'
        })(req, res, function() {
            req.flash('success', 'You are now logged in!');
            res.redirect('/stores');
        });
};

exports.isLoggedIn = (req, res, next) => {
    if (req.isAuthenticated()) {
        next();
        return;
    }
    req.flash('error', 'You must be logged in to do that!');
    res.redirect('/login');
};

exports.logout = (req, res) => {
    req.logout(function(err) {
        if (err) {
            next(err);
            return;
        }
        req.flash('success', 'You are now logged out!');
        res.redirect('/stores');
    });
};

exports.loginForm = (req, res) => {
    res.render('login', { title: 'Login' });
};

exports.forgot = async (req, res) => {
    // 1. Verificar si existe un usuario con ese correo electrónico
    const user = await User.findOne({ email: req.body.email });
    if (!user) {
        req.flash('error', 'No account with that email exists');
        res.redirect('/login');
        return;
    }

    // 2. Establecer el token de restablecimiento y la expiración en esa cuenta
    user.resetPasswordToken = crypto.randomBytes(20).toString('hex');
    user.resetPasswordExpires = Date.now() + 3600000; // 1 hora a partir de ahora
    await user.save(); // Almacenar ambos campos en la base de datos

    // 3. Enviar un correo electrónico con el token
    const resetURL = `http://${req.headers.host}/account/reset/${user.resetPasswordToken}`;
    await mail.send({
        user: user,
        subject: 'Password Reset',
        resetURL: resetURL
    });
    req.flash('success', 'You have been emailed a password reset link.');

    // 4. Redirigir a la página de inicio de sesión
    res.redirect('/login');
};

// Añadimos el metodo para resetear la contraseña
exports.reset = async (req, res) => {
    const user = await User.findOne({
        resetPasswordToken: req.params.token,
        resetPasswordExpires: { $gt: Date.now() }
    });
    if(!user) {
        req.flash('error', 'Password reset is invalid or has expired');
        res.redirect('/login');
        return;
    }
    res.render('reset', { title: 'Reset your Password' });
};

// Reglas personalizadas para express-validator
exports.validationCustomRules = () => {
    return body('password-confirm').custom((value, { req }) => {
        if (value !== req.body.password) {
            throw new Error('Password confirmation does not match');
        }
        return true;
    });
}

// Función de middleware para la actualización de contraseña
exports.validatePassUpdate = (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        // Iterar sobre los errores y generar un mensaje flash para cada uno
        req.flash('error', errors.array().map(err => err.msg));
        res.redirect('back');
        return;
    }
    next(); // Si no hay errores, continuar al siguiente middleware
};

// Actualizamos la contraseña
exports.updatePassword = async (req, res) => {
    const user = await User.findOne({
        resetPasswordToken: req.params.token,
        resetPasswordExpires: { $gt: Date.now() }
    });

    if (!user) {
        req.flash('error', 'Password reset is invalid or has expired');
        res.redirect('/login');
        return;
    }

    // Establecer la nueva contraseña
    await user.setPassword(req.body.password);
    user.resetPasswordToken = undefined;
    user.resetPasswordExpires = undefined;

    // Guardar el usuario actualizado
    const updatedUser = await user.save();

    // Iniciar sesión automáticamente después de actualizar la contraseña
    await new Promise((res, rej) => {
        req.login(updatedUser, (err, data) => {
            if (err) rej(err);
            else res(data);
        });
    });

    req.flash('success', 'Your password has been reset. You are now logged in');
    res.redirect('/stores');
};
