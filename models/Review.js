const mongoose = require('mongoose');
mongoose.Promise = global.Promise; // Tell Mongoose to use ES6 promises

const reviewSchema = new mongoose.Schema({
    created: {
        type: Date,
        default: Date.now
    },
    author: {
        type: mongoose.Schema.ObjectId,
        ref: 'User',
        required: 'You must supply an author'
    },
    store: {
        type: mongoose.Schema.ObjectId,
        ref: 'Store',
        required: 'You must supply a store'
    },
    text: {
        type: String,
        required: 'Your review must have text'
    },
    rating: {
        type: Number,
        min: 1,
        max: 5
    }
});

// Sirve para que cada vez que se haga un find o findOne se haga un populate 
// de la propiedad author, asi no vemos numeros sino su nombre y mail.
function autopopulate(next) {
    this.populate('author');
    next();
}

reviewSchema.pre('find', autopopulate);
reviewSchema.pre('findOne', autopopulate);

module.exports = mongoose.model('Review', reviewSchema);
