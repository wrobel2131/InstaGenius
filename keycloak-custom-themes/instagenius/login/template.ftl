<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true displayRequiredFields=false displayWide=false showAnotherWayIfPresent=true>
    <!DOCTYPE html>
    <html class="${properties.kcHtmlClass!}" <#if locale??>lang="${locale.currentLanguageTag!}"</#if>>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="robots" content="noindex, nofollow">
<#--        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>${msg("loginTitle",(realm.displayName!''))}</title>
        <link rel="icon" href="${url.resourcesPath!}/img/favicon.ico" />
        <link href="${url.resourcesPath!}/css/styles.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body>
    <script src="${url.resourcesPath!}/js/script.js"></script>
        <header>
            <nav class="main-nav">
                <div class="nav-container">
                    <a class="logo-link" href="/">
                        <div class="logo-container">
                            <div class="logo-animation"
                                 (mouseenter)="isHovered = true"
                                 (mouseleave)="isHovered = false">
                                <svg xmlns="http://www.w3.org/2000/svg"
                                     viewBox="0 0 90 90"
                                     class="logo-svg small">
                                    <!-- Infinity-like path with path drawing animation on hover -->
                                    <path class="infinity-path"
                                          d="M30,50 C30,35 45,35 50,45 C55,35 70,35 70,50 C70,65 55,65 50,55 C45,65 30,65 30,50 Z"
                                          stroke="#8b5cf6"
                                          stroke-width="3"
                                          fill="none"
                                          stroke-dasharray="200"
                                          [attr.stroke-dashoffset]="isHovered ? '0' : '200'" />

                                    <!-- Light bulb element with opacity animation -->
                                    <path class="light-bulb"
                                          d="M50,20 L50,30 M40,25 L45,32 M60,25 L55,32"
                                          stroke="#a855f7"
                                          stroke-width="2.5"
                                          stroke-linecap="round" />

                                    <!-- Center circle with bounce animation -->
                                    <circle class="center-circle"
                                            cx="50"
                                            cy="50"
                                            r="8"
                                            stroke="#d8b4fe"
                                            stroke-width="2.5"
                                            fill="none" />

                                    <!-- Brain wave element with flow animation -->
                                    <path class="brain-wave"
                                          d="M25,65 C33,75 40,60 50,70 C60,60 67,75 75,65"
                                          stroke="#c084fc"
                                          stroke-width="2.5"
                                          fill="none"
                                          stroke-linecap="round" />
                                </svg>
                            </div>
                            <span class="logo-text">InstaGenius</span>
                        </div>
                    </a>

                    <!-- Desktop navigation -->
                    <ul class="desktop-menu">
                        <li>
                            <a href="${url.loginUrl!}" class="nav-link">Logowanie</a>
                        </li>
                        <li>
                            <a href="/contact" class="nav-link">Kontakt</a>
                        </li>
                        <li>
                            <a href="${url.registrationUrl!}" class="button primary-button">Rozpocznij za darmo</a>
                        </li>
                    </ul>
                    <div class="mobile-menu-toggle">
                        <button id="menu-toggle-button" class="menu-button">
                            <svg class="menu-icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path id="hamburger-icon" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M4 6h16M4 12h16M4 18h16" />
                                <path id="close-icon" stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M6 18L18 6M6 6l12 12" style="display: none;" />
                            </svg>
                        </button>
                    </div>
                </div>
                <!-- Mobile nav -->
                <ul id="mobile-menu" class="mobile-menu" style="display: none;">
                    <li>
                        <a href="${url.loginUrl!}" class="mobile-nav-link">Logowanie</a>
                    </li>
                    <li>
                        <a href="/contact" class="mobile-nav-link">Kontakt</a>
                    </li>
                    <li>
                        <a href="${url.registrationUrl!}" class="button primary-button mobile-button">Rozpocznij za darmo</a>
                    </li>
                </ul>
            </nav>
        </header>
        <main>

            <#nested "header">
            <#nested "form">
        </main>
        <footer class="footer">
            <div class="footer-container">
                <div class="footer-content">
                    <p class="copyright">
                        © 2025 InstaGenius. Wszelkie prawa zastrzeżone.
                    </p>
                    <div class="footer-links">
                        <a href="/privacy" class="footer-link">Polityka prywatności</a>
                        <a href="/terms" class="footer-link">Warunki użytkowania</a>
                        <a href="/cookies" class="footer-link">Ciasteczka</a>
                    </div>
                </div>
            </div>
        </footer>

    </body>
    </html>
</#macro>