<#import "template.ftl" as layout>
<#import "passkeys.ftl" as passkeys>
<@layout.registrationLayout 
    displayMessage=!messagesPerField.existsError('username','password') 
    displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; 
    section>

    <#if section = "header">
        ${msg("loginAccountTitle")}

    <#elseif section = "form">
        <div id="kc-form" class="login-pf">
            <div id="kc-form-wrapper" class="container">
                <div class="logo">
                    <h1>${msg("loginTitle", realm.displayName!realm.name!)}</h1>
                </div>

                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <#if !usernameHidden??>
                            <div class="input-group">
                                <input tabindex="2" id="username" name="username" value="${(login.username!'')}" 
                                       type="text" placeholder="${msg('usernameOrEmail')}"
                                       autofocus autocomplete="username"
                                       aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>"/>
                                <#if messagesPerField.existsError('username','password')>
                                    <span id="input-error" class="alert alert-error" aria-live="polite">
                                        ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                    </span>
                                </#if>
                            </div>
                        </#if>

                        <div class="input-group">
                            <input tabindex="3" id="password" name="password" type="password" placeholder="${msg('password')}"
                                   autocomplete="current-password"
                                   aria-invalid="<#if messagesPerField.existsError('username','password')>true</#if>"/>
                            <#if usernameHidden?? && messagesPerField.existsError('username','password')>
                                <span id="input-error" class="alert alert-error" aria-live="polite">
                                    ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                                </span>
                            </#if>
                        </div>

                        <div class="checkbox">
                            <#if realm.rememberMe && !usernameHidden??>
                                <label>
                                    <#if login.rememberMe??>
                                        <input tabindex="5" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                    <#else>
                                        <input tabindex="5" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                    </#if>
                                </label>
                            </#if>
                        </div>

                        <input type="hidden" id="id-hidden-input" name="credentialId" 
                               <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>

                        <!-- Use custom styled Sign-In button -->
                        <!-- -->
                        <button tabindex="7" id="kc-login" name="login" type="submit" class="login-btn">
                            ${msg("doLogIn")}
                        </button>

                        <div class="additional-links">
                            <#if realm.resetPasswordAllowed>
                                <a href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a>
                            </#if>
                            <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                                <span> | </span>
                                <a href="${url.registrationUrl}">${msg("doRegister")}</a>
                            </#if>
                        </div>
                    </form>
                </#if>
            </div>
        </div>

        <@passkeys.conditionalUIData />
        <script type="module" src="${url.resourcesPath}/js/passwordVisibility.js"></script>

    <#elseif section = "socialProviders">
        <#if realm.password && social?? && social.providers?has_content>
            <div id="kc-social-providers" class="container fade-in">
                <hr/>
                <h2>${msg("identity-provider-login-label")}</h2>

                <#list social.providers as p>
                    <a data-once-link 
                       id="social-${p.alias}" 
                       class="social-provider-btn" 
                       href="${p.loginUrl}">
                        <#if p.iconClasses?has_content>
                            <i class="${p.iconClasses!}" aria-hidden="true"></i> ${p.displayName!}
                        <#else>
                            ${p.displayName!}
                        </#if>
                    </a>
                </#list>
            </div>
        </#if>
    </#if>

</@layout.registrationLayout>
