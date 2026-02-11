import axios from 'axios';
import dompurify from 'dompurify';

function searchResultsHTML(stores) {
  return stores.map(store => {
    return `
      <a href="/store/${store.slug}" class="search__result">
        <strong>${store.name}</strong>
      </a>
    `;
  }).join('');
}

function typeAhead(search) {
  if (!search) return;

  const searchInput = search.querySelector('input[name="search"]');
  const searchResults = search.querySelector('.search__results');

  searchInput.addEventListener('input', function () {
    // Si no hay input, oculta el div de resultados de búsqueda
    if (!this.value) {
      searchResults.style.display = 'none';
      return;
    }

    // Muestra el div de resultados de búsqueda
    searchResults.style.display = 'block';

    axios.get(`/api/v1/search?q=${this.value}`)
      .then(res => {
        if (res.data.length) {
          const html = searchResultsHTML(res.data.stores);
          searchResults.innerHTML = dompurify.sanitize(html);
        } else {
          searchResults.innerHTML = `
            <div class="search__result">
              <strong>No results found</strong>
            </div>
          `;
        }
      })
      .catch(err => {
        console.log(err);
      });
  });

  searchInput.addEventListener('keyup', function (e) {
    // Manejo de teclas: 38 -> up, 40 -> down, 13 -> enter
    if (![38, 40, 13].includes(e.keyCode)) {
      return; // Ignorar otras teclas
    }

    const current = search.querySelector('.search__result--active');
    const items = search.querySelectorAll('.search__result');
    let next;

    if (e.keyCode === 40 && current) {
      next = current.nextElementSibling || items[0];
    } else if (e.keyCode === 40) {
      next = items[0];
    } else if (e.keyCode === 38 && current) {
      next = current.previousElementSibling || items[items.length - 1];
    } else if (e.keyCode === 38) {
      next = items[items.length - 1];
    } else if (e.keyCode === 13 && current && current.href) {
      window.location = current.href;
      return;
    }

    if (current) {
      current.classList.remove('search__result--active');
    }
    if (next) {
      next.classList.add('search__result--active');
    }
  });
}

export default typeAhead;
