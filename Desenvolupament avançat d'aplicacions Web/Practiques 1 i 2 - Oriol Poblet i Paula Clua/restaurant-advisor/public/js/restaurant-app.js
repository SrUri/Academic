import { content } from './modules/test';
import typeahead from './modules/typeAhead';

console.log('It works!');
console.log(content);

typeahead(document.querySelector('.form-inline'));
