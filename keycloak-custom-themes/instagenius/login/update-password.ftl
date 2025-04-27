<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "header">
        <h1 class="welcome-text">Update Password</h1>
    <#elseif section = "form">
        <div id="kc-form">
            <div id="kc-form-wrapper">
                <form id="kc-passwd-update-form" action="${url.loginAction}" method="post">
                    <div class="form__controls">
                        <div class="form__instructions">
                            <p>Create a new password for your account.</p>
                        </div>

                        <input type="text" id="username" name="username" value="${username}" autocomplete="username"
                               style="display:none;"/>

                        <div class="form__control">
                            <label for="password-new" class="${properties.kcLabelClass!}">New Password</label>
                            <div class="input-wrapper">
                                <input type="password" id="password-new" name="password-new" class="${properties.kcInputClass!}"
                                       autofocus autocomplete="new-password"
                                       aria-invalid="<#if messagesPerField.existsError('password','password-confirm')>true</#if>"/>
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

                        <div class="form__control">
                            <label for="password-confirm" class="${properties.kcLabelClass!}">Confirm Password</label>
                            <div class="input-wrapper">
                                <input type="password" id="password-confirm" name="password-confirm"
                                       class="${properties.kcInputClass!}" autocomplete="new-password"
                                       aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>"/>
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

                        <div id="kc-form-buttons" class="form__buttons">
                            <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} button primary"
                                   type="submit" value="Update Password"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    <#elseif section = "info" >
        <#if realm.duplicateEmailsAllowed>
            ${msg("updatePasswordInfoDupUsername")}
        <#else>
            ${msg("updatePasswordInfo")}
        </#if>
    </#if>
</@layout.registrationLayout>