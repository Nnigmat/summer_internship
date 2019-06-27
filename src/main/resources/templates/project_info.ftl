<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Project info">
    <!-- Description of project top side -->
    <div class="row">
        <div class="col-lg-8">
            <h1 id="1"><@p.text "1" "${project.name}"/></h1>
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

    <!-- Description of project right side -->
    <div class="row">
        <p class="col-lg-12" id="2"><@p.text "2" "${project.description}"/></p>
    </div>

    <div class="row">
        <div class="col-lg-9">
            <i class="text-muted">Who liked:</i> <#list project.who_liked as user>
                <a id="${user.id}list0"><@p.text "${user.id}list0" "${user.username}"/></a><#sep>, <#else> ... </#list>
        </div>
        <div class="col-lg-9">
            <i class="text-muted">Tags:</i>
            <#list project.tags as tag>
                #${tag.text}
                <#sep>; <#else> No tags
            </#list>
        </div>
        <div class="col-lg-3 list-group list-group-flush">
            <span class="list-group-item"><i class="text-muted">Created:</i>
                <a id="3"><@p.text "3" "${project.date_created}"/></a><br></span>
            <span class="list-group-item"><i class="text-muted">Creator:</i>
                <a id="4"><@p.text "4" "${project.creator.username}"/></a><br></span>
            <#if user_now.isCurator()>
                <#list project.type as t>
                    <span class="list-group-item"><i class="text-muted">Type:</i> ${t}<br></span>
                </#list>
            </#if>
        </div>
    </div>
    <hr>

    <!-- Control zone -->
    <#if user_now.isCurator()>
        <div class="form-row my-3">
            <div class="col">
                <button type="button" class="btn btn-primary"
                        data-toggle="modal" data-target="#changeType">Change type
                </button>
            </div>
            <div class="col">
                <button type="button" class="btn btn-primary"
                        data-toggle="modal" data-target="#deleteProject">Delete
                </button>
            </div>
            <div class="col">
                <button type="button" class="btn btn-primary"
                        data-toggle="modal" data-target="#addTag">Add tag
                </button>
            </div>
        </div>
    </#if>

    <!-- Modals -->
    <@m.modal "changeType" "type" "Change" "Change type of this project">
        <div class="input-group mb3">
            <form method="post" action="/project/${project.id}/type" id="type" class="form-inline">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="selectType">Types</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <select id="selectType" name="type" class="custom-select">
                    <option selected>Choose...</option>
                    <#list types as type>
                        <option>${type}</option>
                    </#list>
                </select>
            </form>
        </div>
    </@m.modal>

    <@m.modal "deleteProject" "delete" "Confirm" "Are you sure?">
        <div class="input-group mb3">
            <form method="post" action="/project/${project.id}/delete" id="delete" class="form-inline">
                <p>This project will be absolutely destroyed...</p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
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

    <@m.modal "addTag" "tag" "Add" "Add tag to this project">
        <div class="input-group mb3">
            <form method="post" action="/project/${project.id}/tag" id="tag" class="form-inline">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="selectType">Tags</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <select id="selectType" name="tag" class="custom-select">
                    <option selected>Choose...</option>
                    <#list tags as tag>
                        <option>${tag.text}</option>
                    </#list>
                </select>
            </form>
        </div>
    </@m.modal>

    <!-- Send comment -->
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
                    <div class="col-lg-2 mr-2">
                        <i class="text-muted" id="${comment.id}list1">
                            <@p.text "${comment.id}list1" "${comment.creator.username}: "/></i>
                    </div>
                    <div class="col-lg-7" id="${comment.id}list2">
                        <@p.text "${comment.id}list2" "${comment.text}"/>
                    </div>
                    <div class="col-lg-3 text-muted">
                        <i>${comment.date_created}</i>
                    </div>
                </div>
            </li>
        <#else>
            <span class="list-group-item text-muted">no comments</span>
        </#list>
    </ul>
</@p.page>