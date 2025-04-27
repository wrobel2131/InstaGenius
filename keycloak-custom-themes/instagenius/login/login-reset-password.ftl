<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true displayMessage=!messagesPerField.existsError('username'); section>
    <#if section = "header">
        <h1 class="welcome-text">Reset Password</h1>
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-reset-password-form" action="${url.loginAction}" method="post">
                    <div class="form__controls">
                        <div class="form__instructions">
                            <p>Enter your username or email address and we'll send you instructions to reset your password.</p>
                        </div>

                        <div class="form__control">
                            <label for="username" class="${properties.kcLabelClass!}">
                                <#if !realm.loginWithEmailAllowed>
                                    Username
                                <#elseif !realm.registrationEmailAsUsername>
                                    Username or Email
                                <#else>
                                    Email
                                </#if>
                            </label>
                            <div class="input-wrapper">
                                <input type="text" id="username" name="username" class="${properties.kcInputClass!}"
                                       autofocus value="${(auth.attemptedUsername!'')}"
                                       aria-invalid="<#if messagesPerField.existsError('username')>true</#if>"/>
                                <div class="input-icon">
                                    <i class="fa-regular fa-envelope"></i>
                                </div>
                            </div>
                            <#if messagesPerField.existsError('username')>
                                <span id="input-error-username" class="form__error" aria-live="polite">
                                    ${kcSanitize(messagesPerField.get('username'))?no_esc}
                                </span>
                            </#if>
                        </div>

                        <div id="kc-form-buttons" class="form__buttons">
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} button primary"
                                   type="submit" value="Send Instructions"/>
                        </div>
                    </div>
                </form>

                <div class="divider-text">
                    <span>or</span>
                </div>

                <div class="registration-section">
                    <p class="registration-text">
                        <a href="${url.loginUrl}" class="registration-link">Back to Login</a>
                    </p>
                </div>
            </div>
        </div>
    <#elseif section = "info" >
        <#if realm.duplicateEmailsAllowed>
            ${msg("emailInstructionsDupUsername")}
        <#else>
            ${msg("emailInstructions")}
        </#if>
    </#if>
</@layout.registrationLayout>