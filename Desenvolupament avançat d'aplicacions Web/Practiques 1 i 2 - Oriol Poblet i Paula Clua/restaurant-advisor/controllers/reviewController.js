const mongoose = require('mongoose');
const Review = mongoose.model('Review');
const Store = mongoose.model('Store');

exports.addReview = async (req, res) => {
    req.body.author = req.user._id;
    req.body.store = req.params.id;

    // Guardar la nueva review
    const newReview = new Review(req.body);
    await newReview.save();

    // Recalcular la calificación promedio
    const reviews = await Review.find({ store: req.params.id });
    const averageRating = reviews.reduce((total, review) => total + review.rating, 0) / reviews.length;

    // Actualizar el campo averageRating del restaurante
    await Store.findOneAndUpdate(
        { _id: req.params.id },
        { averageRating: averageRating.toFixed(1) }, // Guardar con un decimal
        { new: true }
    );

    req.flash('success', 'Review Saved!');
    res.redirect('back');
};
