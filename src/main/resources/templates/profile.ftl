<#import "fragments/page.ftl" as p>

<@p.page "Profile">
    <form action="/profile" method="post">
        <!-- Username field -->
        <div class="form-group">
            <label for="username">
                Current username: ${user_now.username}
            </label>
            <input type="text" name="username" class="form-control" placeholder="Enter username"/>
        </div>

        <!-- Password field -->
        <div class="form-group">
            <label for="password">
                Current password: ${user_now.password}
            </label>
            <input type="text" name="password" class="form-control" placeholder="Enter password"/>
        </div>

        <!-- Button field -->
        <div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-2">Edit profile</button>
        </div>
    </form>

    <!-- Authority field -->
    <h4>Authorities:</h4>
    <#list user_now.roles as role>
        <p>${role}</p>
    </#list>

    <!-- Projects field -->
    <h4>Projects:</h4>
    <#list user_now.project_list as project>
        <div>${project.name}</div>
    <#else>
        No projects
    </#list>
</@p.page>