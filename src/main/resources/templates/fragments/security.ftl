<#assign known = Session.SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    password = user.getPassword()
    isAdmin = user.isAdmin()>
<#else>
    <#assign name = "none"
    isAdmin = false>
</#if>