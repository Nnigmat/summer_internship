<#import "fragments/page.ftl" as p>

<@p.page "Users List">
    <h1>Users</h1>
    <hr>
    <!-- User search -->
    <form method="post" action="/user">
        <div class="row my-4">
            <div class="col-10">
                <input type="text" class="form-control" placeholder="User's name" name="username">
            </div>
            <div class="col-2">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </form>

    <!-- Table of users -->
    <#if error??>
        <h3>
            ${error}
        </h3>
    <#else>
        <div style="font-weight: bold; font-size: 20px;">List of all users in system:</div>
        <table class="table mt-4 table-bordered table-hover">
            <thead class="thead-dark">
            <tr>
                <th>Username</th>
                <th>Authority</th>
                <th colspan="3">Action</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <!-- Mark users with authorities -->
                <#assign containsAdmin = false containsMod = false containsCurator = false>
                <#list user.roles as role>
                    <#if role.toString() == "ADMIN"><#assign containsAdmin = true></#if>
                    <#if role.toString() == "MODERATOR"><#assign containsMod = true></#if>
                    <#if role.toString() == "CURATOR"><#assign containsCurator = true></#if>
                </#list>
                <#if containsAdmin>
                    <tr class="table-danger">
                <#else>
                    <#if containsMod>
                        <tr class="table-success">
                    <#else>
                        <#if containsCurator>
                            <tr class="table-info">
                        <#else>
                            <tr class="table-secondary">
                        </#if>
                    </#if>
                </#if>
                <!-- Body -->
                <td>${user.username}</td>
                <td>
                    <#list user.roles as role>
                        ${role}<#sep>;
                    </#list>
                </td>
                <#if !containsAdmin>
                    <td><a href="/user/${user.id}/mod">Moderator</a></td>
                    <td><a href="/user/${user.id}/cur">Curator</a></td>
                    <td><a href="/user/${user.id}/ban">Ban</a></td>
                <#else>
                    <td>Can't</td>
                    <td>touch</td>
                    <td>this</td>
                </#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</@p.page>