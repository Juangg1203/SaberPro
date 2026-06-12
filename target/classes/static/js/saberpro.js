// ── SIDEBAR TOGGLE ────────────────────────────────────────────
document.getElementById('sidebarToggle')?.addEventListener('click', () => {
  document.getElementById('sidebar')?.classList.toggle('open');
});

// ── ACTIVE NAV LINK ───────────────────────────────────────────
(function() {
  const path = window.location.pathname;
  document.querySelectorAll('.sp-nav-link').forEach(link => {
    if (link.getAttribute('href') && path.startsWith(link.getAttribute('href'))) {
      link.classList.add('active');
    }
  });
})();

// ── TABLE SEARCH ──────────────────────────────────────────────
const searchInput = document.getElementById('tableSearch');
if (searchInput) {
  searchInput.addEventListener('input', function() {
    const q = this.value.toLowerCase();
    document.querySelectorAll('.sp-searchable tbody tr').forEach(row => {
      row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
    });
  });
}

// ── CONFIRM DELETE ────────────────────────────────────────────
document.querySelectorAll('[data-confirm]').forEach(el => {
  el.addEventListener('click', e => {
    if (!confirm(el.dataset.confirm || '¿Confirmar acción?')) e.preventDefault();
  });
});

// ── AUTO-DISMISS ALERTS ───────────────────────────────────────
setTimeout(() => {
  document.querySelectorAll('.alert').forEach(a => {
    if (!a.classList.contains('alert-danger')) {
      a.style.transition = 'opacity .5s';
      a.style.opacity = '0';
      setTimeout(() => a.remove(), 500);
    }
  });
}, 3500);

// ── SELECT FACULTAD → NOMBRE ──────────────────────────────────
const selectFacultad = document.getElementById('selectFacultad');
if (selectFacultad) {
  selectFacultad.addEventListener('change', function() {
    const opt = this.options[this.selectedIndex];
    const hiddenNombre = document.getElementById('facultadNombre');
    if (hiddenNombre && opt) hiddenNombre.value = opt.text;
  });
}
