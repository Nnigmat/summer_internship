<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Profile">

    <h1 class="row">User Profile</h1>
    <hr>
    <div class="row text-large">

        <!-- User's info -->
        <div class="col-lg-4">
            <div class="row">
                <div class="col-lg-8">
                    <span class="text-muted">Username: </span><a id="1"><@p.text "1" "${user_now.username}"/></a><br>
                    <span class="text-muted">Password: </span><a id="2"><@p.text "2" "${user_now.password}"/></a><br>
                    <span class="text-muted">Name: </span><a id="3"><@p.text "3" "${user_now.name}"/></a><br>
                    <span class="text-muted">Surname: </span><a id="4"><@p.text "4" "${user_now.surname}"/></a><br>
                    <span class="text-muted">Role: </span>
                    <#list user_now.getAuthorities() as role>
                        ${role}
                    </#list>
                </div>
                <div class="col-lg-4">
                    <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#editProfileModal"> Edit </button>
                </div>
            </div>
        </div>

        <!-- Edit modal -->
        <@m.modal "editProfileModal" "profile" "Update" "Edit information">
            <form method="post" action="/profile" id="profile">
                <div class="form-group">
                    <label for="name" class="col-form-label">Username:</label>
                    <input type="text" class="form-control" name="username">
                </div>
                <div class="form-group">
                    <label for="password" class="col-form-label">Password:</label>
                    <input class="form-control" name="password" type="password">
                </div>
                <div class="form-group">
                    <label for="name" class="col-form-label">Name:</label>
                    <input class="form-control" name="name" type="text">
                </div>
                <div class="form-group">
                    <label for="surname" class="col-form-label">Surname:</label>
                    <input class="form-control" name="surname" type="text">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            </form>
        </@m.modal>

        <div class="col-lg-8 text-left">
            <div class="row">

                <!-- Projects user created -->
                <div class="col-lg-6">
                    <div class="list-group list-group-flush shadow">
                       <span class="list-group-item active">Suggested projects</span>
                        <#list user_now.getCreatedProjects() as project>
                            <a href="/project/${project.id}" class="list-group-item list-group-item-action"
                               id="${project.id}list1">
                                <@p.text "${project.id}list1" "${project.name}"/>
                            </a>
                        </#list>
                    </div>
                </div>

                <!-- Projects user is a part of a team -->
                <#if user_now.isCurator() || user_now.isUser()>
                    <div class="col-lg-6">
                        <div class="list-group list-group-flush shadow">
                            <#if !user_now.isCurator()>
                                <li class="list-group-item list-group-item-action active">Participated in projects</li>
                            <#else>
                                <li class="list-group-item list-group-item-action active">Curator of projects</li>
                            </#if>
                            <#list projects as project>
                                <a href="/intensive/${project.intensive.id}/project/${project.project.id}"
                                   class="list-group-item list-group-item-action" id="${project.id}list2">
                                    <@p.text "${project.id}list2" "${project.intensive.name}: ${project.project.name}"/>
                                </a>
                            </#list>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <div class="mt-4">
        <form method="post" action="/profile/upload" id="profile" enctype="multipart/form-data">
            <input name="file" type="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Upload</button>
        </form>
    </div>
    <div>
        <#if user_now.avatar??>
            <img src="/image/${user_now.avatar}">
            <p>${user_now.avatar}</p>
        </#if>
    </div>
</@p.page>
