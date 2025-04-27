<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo displayWide=(realm.password && social.providers??); section>
    <#if section = "header">
        <h1 class="welcome-text">Welcome!</h1>
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <div class="form__controls">
                            <div class="form__control">
                                <label for="username" class="${properties.kcLabelClass!}">
                                    Login
                                </label>
                                <div class="input-wrapper">
                                    <input tabindex="1" id="username" class="${properties.kcInputClass!}" name="username"
                                           value="${(login.username!'')}" type="text" placeholder="" autocomplete="off"
                                           aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-user"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('username','password')>
                                    <span id="input-error" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                    </span>
                                </#if>
                            </div>

                            <div class="form__control">
                                <label for="password" class="${properties.kcLabelClass!}">Password</label>
                                <div class="input-wrapper">
                                    <input tabindex="2" id="password" class="${properties.kcInputClass!}" name="password"
                                           type="password" placeholder="" autocomplete="off"
                                           aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-eye-slash"></i>
                                    </div>
                                </div>
                            </div>

                            <#if realm.rememberMe && !usernameEditDisabled??>
                                <div class="checkbox">
                                    <label>
                                        <#if login.rememberMe??>
                                            <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                        <#else>
                                            <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                        </#if>
                                    </label>
                                </div>
                            </#if>

                            <#if realm.resetPasswordAllowed>
                                <div class="forgot-password">
                                    <span>Forgot your password?</span>
                                    <a href="${url.loginResetCredentialsUrl}" class="forgot-password-link">Click here!</a>
                                </div>
                            </#if>

                            <div id="kc-form-buttons" class="form__buttons">
                                <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                                <input tabindex="4" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!}" name="login" id="kc-login" type="submit" value="Sign in"/>
                            </div>
                        </div>
                    </form>
                </#if>

                <#-- Show divider and social providers only if they exist -->
                <#if social.providers?? && social.providers?size gt 0>
                    <div class="divider-text">
                        <span>or</span>
                    </div>

                    <div id="kc-social-providers" class="social-buttons">
                        <#list social.providers as p>
                            <#if p.alias = 'google'>
                                <a href="${p.loginUrl}" id="social-${p.alias}" class="social-login-button ${p.alias}-login-button">
                                    <svg class="google-icon" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M23.04 12.2615C23.04 11.446 22.9668 10.6619 22.8309 9.90918H12V14.3576H18.1891C17.9225 15.7951 17.1123 17.013 15.8943 17.8285V20.714H19.6109C21.7855 18.7119 23.04 15.7637 23.04 12.2615Z" fill="#4285F4"/>
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M12 23.4998C15.105 23.4998 17.7081 22.47 19.6109 20.7137L15.8943 17.8282C14.8645 18.5182 13.5472 18.9259 12 18.9259C9.00474 18.9259 6.46951 16.903 5.56519 14.1848H1.72314V17.1644C3.61542 20.9228 7.50451 23.4998 12 23.4998Z" fill="#34A853"/>
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M5.56523 14.185C5.33523 13.495 5.20455 12.7579 5.20455 12C5.20455 11.242 5.33523 10.505 5.56523 9.81499V6.83545H1.72318C0.944318 8.38795 0.5 10.1443 0.5 12C0.5 13.8557 0.944318 15.612 1.72318 17.1645L5.56523 14.185Z" fill="#FBBC05"/>
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M12 5.07386C13.6884 5.07386 15.2043 5.65409 16.3961 6.79364L19.6945 3.49523C17.7029 1.63955 15.0997 0.5 12 0.5C7.50451 0.5 3.61542 3.07705 1.72314 6.83545L5.56519 9.815C6.46951 7.09682 9.00474 5.07386 12 5.07386Z" fill="#EA4335"/>
                                    </svg>
                                    <span class="social-text">Continue with Google</span>
                                </a>
                            <#elseif p.alias = 'github'>
                                <a href="${p.loginUrl}" id="social-${p.alias}" class="social-login-button ${p.alias}-login-button">
                                    <i class="fa-brands fa-github fa-lg"></i>
                                    <span class="social-text">Continue with GitHub</span>
                                </a>
                            <#elseif p.alias = 'apple'>
                                <a href="${p.loginUrl}" id="social-${p.alias}" class="social-login-button ${p.alias}-login-button">
                                    <i class="fa-brands fa-apple fa-lg"></i>
                                    <span class="social-text">Continue with Apple</span>
                                </a>
                            <#else>
                                <a href="${p.loginUrl}" id="social-${p.alias}" class="social-login-button ${p.alias}-login-button">
                                    <span class="social-text">Continue with ${p.displayName}</span>
                                </a>
                            </#if>
                        </#list>
                    </div>
                </#if>

                <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                    <#if social.providers?? && social.providers?size gt 0>
                    <#else>
                        <div class="divider-text">
                            <span>or</span>
                        </div>
                    </#if>
                    <div class="registration-section">
                        <p class="registration-text">
                            No account?
                            <a href="${url.registrationUrl}" class="registration-link">Sign up!</a>
                        </p>
                    </div>
                </#if>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>