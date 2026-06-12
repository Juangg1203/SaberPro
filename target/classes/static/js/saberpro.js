/* ================================================================
   SABER PRO UTS — saberpro.js  |  Interactividad global
================================================================ */

document.addEventListener('DOMContentLoaded', () => {

    // ── 1. Sidebar toggle ──────────────────────────────────────────
    const toggleBtn = document.getElementById('sidebarToggle');
    const sidebar   = document.querySelector('.sp-sidebar');
    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener('click', () => {
            sidebar.classList.toggle('open');
        });
        // Cerrar al hacer clic afuera (móvil)
        document.addEventListener('click', (e) => {
            if (sidebar.classList.contains('open') &&
                !sidebar.contains(e.target) &&
                !toggleBtn.contains(e.target)) {
                sidebar.classList.remove('open');
            }
        });
    }

    // ── 2. Marcar nav-link activo según URL ──────────────────────────
    const currentPath = window.location.pathname;
    document.querySelectorAll('.sp-nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href && currentPath.startsWith(href) && href !== '/') {
            link.classList.add('active');
        }
    });

    // ── 3. Búsqueda en tablas (.sp-searchable) ───────────────────────
    document.querySelectorAll('[id^="tableSearch"]').forEach(input => {
        input.addEventListener('input', function () {
            const term  = this.value.toLowerCase().trim();
            const table = this.closest('.sp-card, .sp-card-body')
                            ?.querySelector('.sp-searchable');
            if (!table) return;
            table.querySelectorAll('tbody tr').forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = (!term || text.includes(term)) ? '' : 'none';
            });
        });
    });

    // ── 4. Auto-dismiss alerts ────────────────────────────────────────
    document.querySelectorAll('.alert:not(.alert-permanent)').forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity .5s, transform .5s';
            alert.style.opacity    = '0';
            alert.style.transform  = 'translateY(-8px)';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });

    // ── 5. Confirmar eliminaciones ────────────────────────────────────
    document.querySelectorAll('[data-confirm]').forEach(el => {
        el.addEventListener('click', function (e) {
            const msg = this.dataset.confirm || '¿Estás seguro?';
            if (!confirm(msg)) e.preventDefault();
        });
    });

    // ── 6. Tooltips Bootstrap (si están disponibles) ──────────────────
    if (typeof bootstrap !== 'undefined' && bootstrap.Tooltip) {
        document.querySelectorAll('[title]:not([data-bs-toggle])').forEach(el => {
            new bootstrap.Tooltip(el, { trigger: 'hover', placement: 'top' });
        });
    }

    // ── 7. Animación de contadores en métricas ────────────────────────
    document.querySelectorAll('.sp-metric-val').forEach(el => {
        const target = parseInt(el.textContent.replace(/\D/g, '')) || 0;
        if (target === 0 || target > 9999) return;
        let current  = 0;
        const step   = Math.ceil(target / 30);
        const timer  = setInterval(() => {
            current = Math.min(current + step, target);
            el.textContent = current;
            if (current >= target) clearInterval(timer);
        }, 30);
    });

    // ── 8. Botones de confirmación con spinner ────────────────────────
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function () {
            const btn = this.querySelector('[type="submit"]');
            if (btn && !btn.dataset.noSpinner) {
                btn.disabled = true;
                const originalHtml = btn.innerHTML;
                btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status"></span>Procesando...';
                setTimeout(() => {
                    btn.disabled = false;
                    btn.innerHTML = originalHtml;
                }, 8000);
            }
        });
    });

    // ── 9. Resaltar fila de tabla al seleccionar ──────────────────────
    document.querySelectorAll('.sp-table tbody tr').forEach(row => {
        row.style.cursor = 'default';
        row.addEventListener('click', function () {
            document.querySelectorAll('.sp-table tbody tr.sp-row-selected')
                .forEach(r => r.classList.remove('sp-row-selected'));
            this.classList.add('sp-row-selected');
        });
    });

});

/* Estilo para fila seleccionada */
const style = document.createElement('style');
style.textContent = '.sp-row-selected{background:linear-gradient(135deg,#d1fae5,#f0fdf4)!important;outline:2px solid #6ee7b7;}';
document.head.appendChild(style);
