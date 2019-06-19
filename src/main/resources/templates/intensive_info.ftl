<#import "fragments/page.ftl" as p>

<@p.page "${intensive.name}">
    <!-- Description of intensive -->
    <h5>${intensive.name}</h5>
    <p>${intensive.description}</p>
    <p>Start: ${intensive.date_start}</p>
    <p>End: ${intensive.date_end}</p>
    <p>Curator: ${intensive.curator.username}</p>

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

    <!-- List of projects -->
    <div class="row">
        <#list projects as project>
            <div class="col-4">
                <div class="card my-2" style="width: 18rem;">
                    <div class="card-body">
                        <h5 class="card-title">${project.name}</h5>
                        <p class="card-text">
                            ${project.description}
                        </p>
                        <a href="/project/${project.id}">Edit >></a>
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