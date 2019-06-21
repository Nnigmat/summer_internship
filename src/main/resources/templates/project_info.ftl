<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "${project.name}">
    <div class="row">
        <div class="col-lg-8">
            <h1>${project.name}</h1>
        </div>
        <div class="col-lg-4">
            <#if user_now.isModerator() || project.isCreator(user_now)>
                <button type="button" class="btn btn-primary float-right" data-toggle="modal"
                        data-target="#editProjectModal"> Edit
                </button>
            </#if>
        </div>
    </div>
    <hr>
    <!-- Description of intensive -->
    <div class="row">
        <p class="col-lg-12">${project.description}</p>
    </div>

    <div class="row">
        <div class="col-lg-9">
            <!-- Team -->
            <i class="text-muted">Team:</i> <#list project.team as user> ${user.username}<#sep>, <#else> ... </#list>
            <!-- Add users -->
            <#if user_now.isCurator()>
                <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#addUserModal">Add user</button>
            </#if>
        </div>
        <div class="col-lg-3 list-group list-group-flush">
            <span class="list-group-item"><i class="text-muted">Created:</i> ${project.date_created}<br></span>
            <span class="list-group-item"><i class="text-muted">Curator:</i> ${project.creator.username}<br></span>
            <span class="list-group-item"><i
                        class="text-muted">Supervisor:</i> <#if project.supervisor??>${project.supervisor.username}<#else>...</#if></span>
        </div>
    </div>
    <hr>

    <@m.modal "addUserModal" "user" "Add" "Add User">
        <div class="input-group mb3">
        <form method="post" action="/project/${project.id}/add" id="user" class="form-inline">
            <div class="input-group-prepend">
                <label class="input-group-text" for="inputGroupSelect01">Options</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <select id="selectUser" name="username" class="custom-select">
                <option selected>Choose...</option>
                <#list all_users as user>
                    <option>${user.username}</option>
                </#list>
            </select>
        </div>
        </form>
    </@m.modal>

    <@m.modal "editProjectModal" "project" "Update" "Edit project information">
        <form method="post" action="/project/${project.id}/edit" id="project">
            <div class="form-group">
                <label for="name" class="col-form-label">Name of project:</label>
                <input type="text" class="form-control" name="name">
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Description:</label>
                <textarea class="form-control" name="description"></textarea>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </@m.modal>



    <!-- Add supervisor -->
    <#if user_now.isCurator() && !project.supervisor??>
        <@p.collapse "Add supervisor" "supervisor">
            <form method="post" action="/project/${project.id}/supervisor">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label for="selectSupervisor">Select new participant</label>
                <select id="selectSupervisor" name="username">
                    <#list all_users as user>
                        <option selected>${user.username}</option>
                    </#list>
                </select>
                <button type="submit" class="btn btn-primary mt-2">Add</button>
            </form>
        </@p.collapse>
    </#if>

    <form method="post" action="/project/${project.id}/comment" class="row">
        <div class="col-lg-10">
            <input type="text" name="text" placeholder="Your message" class="form-control my-2"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
        <div class="col-lg-2">
            <button type="submit" class="btn btn-primary mt-2">Send</button>
        </div>
    </form>

    <!-- Comments -->
    <ul class="list-group list-group-flush">
        <#list comments as comment>
            <li class="list-group-item">
                <div class="row">
                    <div class="col-lg-1">
                        <i class="text-muted">${comment.creator.username}: </i>
                    </div>
                    <div class="col-lg-9">
                        ${comment.text}
                    </div>
                    <div class="col-lg-2 text-muted">
                        <i>${comment.date_created}</i>
                    </div>
                </div>
            </li>
        <#else>
            <span class="list-group-item text-muted">no comments</span>
        </#list>
    </ul>
</@p.page>