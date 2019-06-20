<#import "fragments/page.ftl" as p>

<@p.page "${project.name}">
    <!-- Description of intensive -->
    <div class="row">
        <h1>${project.name}</h1>
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

    <!-- Change description -->
    <#if user_now.isModerator() || user_now.id == project.creator.id>
        <@p.collapse "Edit" "edit">
            <form method="post" action="/project/${project.id}/edit">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="text" placeholder="Name of project" name="name" class="form-control my-2"/>
                <input type="text" placeholder="Description of project" name="description" class="form-control my-2"/>
                <button type="submit" class="btn btn-primary mt-2">Update</button>
            </form>
        </@p.collapse>
    </#if>

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