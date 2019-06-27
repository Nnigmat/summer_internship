<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Intensive info">
    <div class="row">
        <div class="col-lg-6">
            <h1 id="1"><@p.text "1" "${intensive.name}"/></h1>
        </div>
        <#if user_now.isCurator() >
             <div class="col-lg-6">
                <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#editIntensiveModal"> Edit </button>
            </div>
        </#if>
    </div>

    <!-- Description of intensive -->
    <div class="row">
        <div class="col-lg-9">
            <p id="2"><@p.text "2" "${intensive.description}"/></p>
        </div>
        <div class="col-lg-3">
            <i>Dates:</i> <a id="3"><@p.text "3" "${intensive.date_start} - ${intensive.date_end}"/></a><br>
            <i>Curator:</i> <a id="5"><@p.text "4" "${intensive.curator.username}"/></a>
        </div>
    </div>

    <!-- Control zone -->
    <#if user_now.isCurator()>
        <div class="form-row my-3">
            <div class="col">
                <button type="button" class="btn btn-primary"
                        data-toggle="modal" data-target="#addProject">Add project
                </button>
            </div>
        </div>
    </#if>

    <!-- Modals -->
    <@m.modal "editIntensiveModal" "intensive" "Update" "Edit intensive">
        <form method="post" action="/intensive/${intensive.id}/edit" id="intensive">
            <div class="form-group">
                <label for="name" class="col-form-label">Name of intensive:</label>
                <input type="text" class="form-control" name="name">
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Description:</label>
                <textarea class="form-control" name="description"></textarea>
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Date of start (YYYY-MM-DD):</label>
                <input class="form-control" name="date_start"></input>
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Date of end (YYYY-MM-DD):</label>
                <input class="form-control" name="date_end"></input>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </@m.modal>

    <@m.modal "addProject" "project" "Add" "Add project">
        <#if isEmpty>
            <p>No new projects available</p>
        </#if>
        <div class="input-group mb3">
            <form method="post" action="/intensive/${intensive.id}" id="project" class="form-inline">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="selectProject">Projects</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <select id="selectProject" name="project_name" class="custom-select">
                    <option selected>Choose...</option>
                    <#list all_projects as item>
                        <option selected id="${item.id}list1"><@p.text "${item.id}list1" "${item.name}"/></option>
                    </#list>
                </select>
            </form>
        </div>
    </@m.modal>

    <!-- List of projects -->
    <div class="row">
        <#list projects as project>
            <div class="col-4">
                <div class="card my-2" style="width: 18rem;">
                    <div class="card-body">
                        <a href="/intensive/${intensive.id}/project/${project.id}" style="color: inherit;"><h5
                                    class="card-title"
                                    id="${project.id}list2">
                                <@p.text "${project.id}list2" "${project.name}"/></h5></a>
                        <p class="card-text" id="${project.id}list3">
                            <@p.text "${project.id}list3" "${project.description}"/>
                        </p>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"
                                id="${project.id}list4"><@p.text "${project.id}list4" "Date: ${project.date_created}"/></li>
                        </ul>
                    </div>
                    <div class="card-footer text-muted" id="${project.id}list5">
                        <@p.text "${project.id}list5" "Creator: ${project.creator.username}"/>
                    </div>
                </div>
            </div>
        <#else>
            <h3>No projects yet</h3>
        </#list>
    </div>
</@p.page>