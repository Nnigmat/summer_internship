<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Registration">
    <#if !user_now??>
        <form method="post" action="/registration/${uuid}">
            <div class="form-group">
                <label for="name" class="col-form-label">Username:</label>
                <input type="text" class="form-control" name="username">
            </div>
            <div class="form-group">
                <label for="password" class="col-form-label">Password:</label>
                <input class="form-control" name="password" type="text">
            </div>
            <div class="form-group">
                <label for="name" class="col-form-label">Name:</label>
                <input class="form-control" name="name" type="text">
            </div>
            <div class="form-group">
                <label for="surname" class="col-form-label">Surname:</label>
                <input class="form-control" name="surname" type="text">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Register</button>
        </form>
    <#else>
        <h3>
            Sorry, but you are already in the system
        </h3>
    </#if>
</@p.page>
