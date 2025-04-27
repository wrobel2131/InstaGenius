<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('firstName','lastName','email','username','password','password-confirm'); section>
    <#if section = "header">
        <h1 class="welcome-text">Create new account!</h1>
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-register-form" action="${url.registrationAction}" method="post">
                    <div class="form__controls">
                        <div class="registration-grid">
                            <#if !realm.registrationEmailAsUsername>
                                <div class="form__control username-field">
                                    <label for="username" class="${properties.kcLabelClass!}">Username</label>
                                    <div class="input-wrapper">
                                        <input type="text" id="username" class="${properties.kcInputClass!}" name="username"
                                               value="${(register.formData.username!'')}" placeholder="" autocomplete="username"
                                               aria-invalid="<#if messagesPerField.existsError('username')>true</#if>" />
                                        <div class="input-icon">
                                            <i class="fa-regular fa-user"></i>
                                        </div>
                                    </div>
                                    <#if messagesPerField.existsError('username')>
                                        <span id="input-error-username" class="form__error" aria-live="polite">
                                            ${kcSanitize(messagesPerField.get('username'))?no_esc}
                                        </span>
                                    </#if>
                                </div>
                            </#if>

                            <div class="form__control email-field">
                                <label for="email" class="${properties.kcLabelClass!}">Email</label>
                                <div class="input-wrapper">
                                    <input type="text" id="email" class="${properties.kcInputClass!}" name="email"
                                           value="${(register.formData.email!'')}" placeholder="" autocomplete="email"
                                           aria-invalid="<#if messagesPerField.existsError('email')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-envelope"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('email')>
                                    <span id="input-error-email" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.get('email'))?no_esc}
                                    </span>
                                </#if>
                            </div>

                            <div class="form__control firstname-field">
                                <label for="firstName" class="${properties.kcLabelClass!}">First Name</label>
                                <div class="input-wrapper">
                                    <input type="text" id="firstName" class="${properties.kcInputClass!}" name="firstName"
                                           value="${(register.formData.firstName!'')}" placeholder=""
                                           aria-invalid="<#if messagesPerField.existsError('firstName')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-user"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('firstName')>
                                    <span id="input-error-firstname" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.get('firstName'))?no_esc}
                                    </span>
                                </#if>
                            </div>

                            <div class="form__control lastname-field">
                                <label for="lastName" class="${properties.kcLabelClass!}">Last Name</label>
                                <div class="input-wrapper">
                                    <input type="text" id="lastName" class="${properties.kcInputClass!}" name="lastName"
                                           value="${(register.formData.lastName!'')}" placeholder=""
                                           aria-invalid="<#if messagesPerField.existsError('lastName')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-user"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('lastName')>
                                    <span id="input-error-lastname" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.get('lastName'))?no_esc}
                                    </span>
                                </#if>
                            </div>

                            <div class="form__control password-field">
                                <label for="password" class="${properties.kcLabelClass!}">Password</label>
                                <div class="input-wrapper">
                                    <input type="password" id="password" class="${properties.kcInputClass!}" name="password"
                                           autocomplete="new-password" placeholder=""
                                           aria-invalid="<#if messagesPerField.existsError('password','password-confirm')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-eye-slash"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('password')>
                                    <span id="input-error-password" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.get('password'))?no_esc}
                                    </span>
                                </#if>
                            </div>

                            <div class="form__control password-confirm-field">
                                <label for="password-confirm" class="${properties.kcLabelClass!}">Confirm Password</label>
                                <div class="input-wrapper">
                                    <input type="password" id="password-confirm" class="${properties.kcInputClass!}"
                                           name="password-confirm" placeholder=""
                                           aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>" />
                                    <div class="input-icon">
                                        <i class="fa-regular fa-eye-slash"></i>
                                    </div>
                                </div>
                                <#if messagesPerField.existsError('password-confirm')>
                                    <span id="input-error-password-confirm" class="form__error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.get('password-confirm'))?no_esc}
                                    </span>
                                </#if>
                            </div>
                        </div>

                        <#if recaptchaRequired??>
                            <div class="form__recaptcha">
                                <div class="g-recaptcha" data-size="compact" data-sitekey="${recaptchaSiteKey}"></div>
                            </div>
                        </#if>

                        <div id="kc-form-buttons" class="form__buttons">
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} button primary" type="submit" value="Sign up"/>
                        </div>
                    </div>
                </form>
                <div class="registration-section">
                    <p class="registration-text">
                        Already have an account?
                        <a href="${url.loginUrl}" class="registration-link">Sign in!</a>
                    </p>
                </div>
            </div>
        </div>
    </#if>
</@layout.registrationLayout>