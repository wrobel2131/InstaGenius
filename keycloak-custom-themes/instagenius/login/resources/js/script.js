document.addEventListener('DOMContentLoaded', function() {
    // Logo animation handling
    const logoAnimation = document.getElementById('logo-animation');
    if (logoAnimation) {
        logoAnimation.addEventListener('mouseenter', function() {
            logoAnimation.querySelectorAll('.infinity-path').forEach(path => {
                path.style.strokeDashoffset = '0';
            });
            logoAnimation.classList.add('hovered');
        });

        logoAnimation.addEventListener('mouseleave', function() {
            logoAnimation.querySelectorAll('.infinity-path').forEach(path => {
                path.style.strokeDashoffset = '200';
            });
            logoAnimation.classList.remove('hovered');
        });
    }

    // Mobile menu toggle handling
    const menuToggleButton = document.getElementById('menu-toggle-button');
    const mobileMenu = document.getElementById('mobile-menu');
    const hamburgerIcon = document.getElementById('hamburger-icon');
    const closeIcon = document.getElementById('close-icon');

    if (menuToggleButton && mobileMenu) {
        menuToggleButton.addEventListener('click', function() {
            const isMenuOpen = mobileMenu.style.display !== 'none';

            if (isMenuOpen) {
                mobileMenu.style.display = 'none';
                hamburgerIcon.style.display = 'block';
                closeIcon.style.display = 'none';
            } else {
                mobileMenu.style.display = 'block';
                hamburgerIcon.style.display = 'none';
                closeIcon.style.display = 'block';
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Funkcja do przełączania widoczności hasła
    const togglePasswordVisibility = () => {
        const passwordFields = document.querySelectorAll('input[type="password"]');
        const toggleIcons = document.querySelectorAll('.fa-eye-slash, .fa-eye');

        toggleIcons.forEach((icon, index) => {
            icon.addEventListener('click', function () {
                // Znajdź odpowiednie pole hasła
                const passwordField = icon.closest('.input-wrapper').querySelector('input');

                // Przełącz typ pola między "password" a "text"
                const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordField.setAttribute('type', type);

                // Zamień ikonę
                if (type === 'password') {
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        });
    };

    // Efekt focusu na ikonie, gdy pole jest aktywne
    const setupInputFocusEffects = () => {
        const inputs = document.querySelectorAll('.input-wrapper input');

        inputs.forEach(input => {
            input.addEventListener('focus', function () {
                const icon = this.parentElement.querySelector('.input-icon i');
                if (icon) {
                    icon.style.color = 'var(--color-purple-700)';
                }
            });

            input.addEventListener('blur', function () {
                const icon = this.parentElement.querySelector('.input-icon i');
                if (icon) {
                    icon.style.color = 'var(--color-gray-600)';
                }
            });
        });
    };

    // Inicjalizacja funkcji
    togglePasswordVisibility();
    setupInputFocusEffects();
});