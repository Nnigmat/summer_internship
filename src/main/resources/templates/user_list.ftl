<#import "fragments/page.ftl" as p>

<@p.page "Users List">
    <h1>Users</h1>
    <hr>
    <!-- User search -->
    <form method="post" action="/user">
        <div class="row my-4">
            <div class="col-10">
                <input type="text" class="form-control" placeholder="User's name/tag" name="username">
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
                <#assign containsAdmin = false containsMod = false containsCurator = false containsBan = false>
                <#if user.role == "ADMIN"><#assign containsAdmin = true></#if>
                <#if user.role == "MODERATOR"><#assign containsMod = true></#if>
                <#if user.role == "CURATOR"><#assign containsCurator = true></#if>
                <#if user.role == "BAN"><#assign containsBan = true></#if>
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
                <td id="${user.id}list1"><@p.text "${user.id}list1" "${user.username}"/></td>
                <td>
                    ${user.role}
                </td>
                <#if !containsAdmin>
                    <#if !containsMod>
                        <td><a href="/user/${user.id}/mod">Make mod</a></td>
                    <#else>
                        <td><a href="/user/${user.id}/mod">Unmod</a></td>
                    </#if>

                    <#if !containsCurator>
                        <td><a href="/user/${user.id}/cur">Make curator</a></td>
                    <#else>
                        <td><a href="/user/${user.id}/cur">Uncurate xD</a></td>
                    </#if>

                    <#if !containsBan>
                        <td><a href="/user/${user.id}/ban">Ban this mf</a></td>
                    <#else>
                        <td><a href="/user/${user.id}/ban">Unban</a></td>
                    </#if>
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

    <form method="post" action="/user/update_link">
        <div class="row my-4">
            <div class="col-10">
                http://localhost:8080/registration/${uuid}
            </div>
            <div class="col-2">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary">Update</button>
            </div>
        </div>
    </form>
</@p.page>