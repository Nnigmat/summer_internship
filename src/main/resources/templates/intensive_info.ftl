<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "${intensive.name}">
    <div class="row">
        <div class="col-lg-6">
            <h1>${intensive.name}</h1>
        </div>
        <#if user_now.isCurator() >
             <div class="col-lg-6">
                <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#editIntensiveModal"> Edit </button>
            </div>
        </#if>
    </div>

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
                <label for="description" class="col-form-label">Date of start:</label>
                <input class="form-control" name="date_start"></input>
            </div>
            <div class="form-group">
                <label for="description" class="col-form-label">Date of end:</label>
                <input class="form-control" name="date_end"></input>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </@m.modal>

    <!-- Description of intensive -->
    <div class="row">
        <div class="col-lg-9">
            <p>${intensive.description}</p>
        </div>
        <div class="col-lg-3">
            <i>Dates:</i> ${intensive.date_start} - ${intensive.date_end} <br>
            <i>Curator:</i> ${intensive.curator.username}
        </div>
    </div>

    <!-- Add project -->
    <#if user_now.isCurator()>
        <@p.collapse "Add project" "project">
            <form method="post" action="/intensive/${intensive.id}">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label for="selectProject">Select project</label>
                <select id="selectProject" name="project">
                    <#list all_projects as project>
                        <option selected>${project.name}</option>
                    </#list>
                </select>
                <button type="submit" class="btn btn-primary mt-2">Add</button>
            </form>
        </@p.collapse>
    </#if>

    <hr>
    <!-- List of projects -->
    <div class="row">
        <#list projects as project>
            <div class="col-4">
                <div class="card my-2" style="width: 18rem;">
                    <div class="card-body">
                        <a href="/project/${project.id}" style="color: inherit;"><h5 class="card-title">${project.name}</h5></a>
                        <p class="card-text">
                            ${project.description}
                        </p>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Date: ${project.date_created}</li>
                        </ul>
                    </div>
                    <div class="card-footer text-muted">
                        Creator: ${project.creator.username}
                    </div>
                </div>
            </div>
        <#else>
            <h3>No projects yet</h3>
        </#list>
    </div>
</@p.page>