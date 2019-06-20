<#import "fragments/page.ftl" as p>

<@p.page "Profile">

    <h1 class="row">User Profile</h1>
    <hr>
    <div class="row text-large">

        <!-- User's info -->
        <div class="col-lg-4">
            <span class="text-muted">Username: </span>${user_now.username}<br>
            <span class="text-muted">Role: </span>
            <#list user_now.getAuthorities() as role>
                ${role}
            </#list>
            <br>


        </div>

        <div class="col-lg-8 text-left">
            <div class="row">

                <!-- Projects user created -->
                <div class="col-lg-6">
                    <div class="list-group list-group-flush shadow">
                       <span class="list-group-item active">Suggested projects</span>
                        <#list user_now.getCreatedProjects() as project>
                            <a href="/project/${project.id}" class="list-group-item list-group-item-action">
                                ${project.name}
                            </a>
                        </#list>
                    </div>
                </div>

                <!-- Projects user is a part of a team -->
                <div class="col-lg-6">
                    <div class="list-group list-group-flush shadow">
                        <li class="list-group-item list-group-item-action active">Participated in projects</li>
                        <#list user_now.getProject_list() as project>
                            <a href="/project/${project.id}" class="list-group-item list-group-item-action">
                                ${project.name}
                            </a>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>


<#--    <form action="/profile" method="post">-->
<#--        <!-- Username field &ndash;&gt;-->
<#--        <div class="form-group">-->
<#--            <label for="username">-->
<#--                Current username: ${user_now.username}-->
<#--            </label>-->
<#--            <input type="text" name="username" class="form-control" placeholder="Enter username"/>-->
<#--        </div>-->

<#--        <!-- Password field &ndash;&gt;-->
<#--        <div class="form-group">-->
<#--            <label for="password">-->
<#--                Current password: ${user_now.password}-->
<#--            </label>-->
<#--            <input type="text" name="password" class="form-control" placeholder="Enter password"/>-->
<#--        </div>-->

<#--        <!-- Button field &ndash;&gt;-->
<#--        <div>-->
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
<#--            <button type="submit" class="btn btn-primary my-2">Edit profile</button>-->
<#--        </div>-->
<#--    </form>-->

<#--    <!-- Authority field &ndash;&gt;-->
<#--    <h4>Authorities:</h4>-->
<#--    <#list user_now.roles as role>-->
<#--        <p>${role}</p>-->
<#--    </#list>-->

<#--    <!-- Projects field &ndash;&gt;-->
<#--    <h4>Projects:</h4>-->
<#--    <#list user_now.project_list as project>-->
<#--        <div>${project.name}</div>-->
<#--    <#else>-->
<#--        No projects-->
<#--    </#list>-->
</@p.page>
