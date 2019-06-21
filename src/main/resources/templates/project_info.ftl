<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "${project.name}">
    <div class="row">
        <div class="col-lg-8">
            <h1>${project.name}</h1>
        </div>
        <div class="col-lg-4">
            <#if user_now.isModerator() || project.isCreator(user_now)>
                <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#editProjectModal"> Edit </button>
            </#if>
        </div>
    </div>
    <!-- Description of intensive -->
    <div class="row">
        <div class="col-lg-9">
            <p>${project.description}</p>
            <!-- Team -->
            Team members:
            <#list project.team as user>
                <div>
                    ${user.username}
                </div>
            <#else>
            <h5>No participants yet</h5>
            </#list>
        </div>
        <div class="col-lg-3">
            <i>Created:</i> ${project.date_created}<br>
            <i>Curator:</i> ${project.creator.username}
        </div>
    </div>
    <hr>

    <@m.modal "editProjectModal" "project" "Update">
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

    <!-- Add users -->
    <#if user_now.isCurator()>
        <@p.collapse "Add participant" "add_user">
            <form method="post" action="/project/${project.id}/add">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label for="selectUser">Select new participant</label>
                <select id="selectUser" name="username">
                    <#list all_users as user>
                        <option selected>${user.username}</option>
                    </#list>
                </select>
                <button type="submit" class="btn btn-primary mt-2">Add</button>
            </form>
        </@p.collapse>
    </#if>


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

    <!-- Supervisor -->
    <#if project.supervisor??>
        <h3>Supervisor: </h3>
        <p>${project.supervisor.username}</p>
    <#else>
        <h5>No supervisor</h5>
    </#if>

    <!-- Add comment -->
    <@p.collapse "Send comment" "add_comment">
        <form method="post" action="/project/${project.id}/comment">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="text" name="text" placeholder="Your message" class="form-control my-2"/>
            <button type="submit" class="btn btn-primary mt-2">Send</button>
        </form>
    </@p.collapse>

    <!-- Comments -->
    <div class="row">
        <#list comments as comment>
            <div class="col-4">
                <div class="card my-2">
                    <div class="card-body">
                        <h5 class="card-title">${comment.creator.username}</h5>
                        <p class="card-text">
                            ${comment.text}
                        </p>
                    </div>
                    <div class="card-footer text-muted">
                        Date: ${comment.date_created}
                    </div>
                </div>
            </div>
        <#else>
            <h5>No comments Kappa</h5>
        </#list>
    </div>
</@p.page>