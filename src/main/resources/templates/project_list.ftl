<#import "fragments/page.ftl" as p>

<@p.page "Projects list">
    <!-- Project types -->
    <div class="row">
        <a href="/project?type=NEW" class="col-lg-3">New projects</a>
        <a href="/project?type=ACCEPTED" class="col-lg-3">Accepted projects</a>
        <a href="/project?type=DECLINED" class="col-lg-3">Declined projects</a>
        <a href="/project?type=ARCHIVED" class="col-lg-3">Archived projects</a>
    </div>


    <!-- Create project -->
    <@p.collapse "New project" "project">
        <form method="post" action="/project">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="text" placeholder="Name of project" name="name" class="form-control my-2"/>
            <input type="text" placeholder="Description of project" name="description" class="form-control my-2"/>
            <button type="submit" class="btn btn-primary mt-2">Create</button>
        </form>
    </@p.collapse>

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
                        <a href="/project/${project.id}">Info >></a>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Date: ${project.date_created}</li>
                            <li class="list-group-item">
                                <#if !project.isIntensiveListEmpty()>
                                    <#list project.intensive_list as intensive>
                                        <div>${intensive.id}</div>
                                    </#list>
                                <#else>
                                    No intensives
                                </#if>
                            </li>
                        </ul>
                    </div>
                    <div class="card-footer text-muted">
                        Creator: ${project.creator.username}
                    </div>
                </div>
            </div>
        <#else>
            No intensives right now zzz
        </#list>
    </div>
</@p.page>