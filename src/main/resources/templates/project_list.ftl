<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Projects list">
    <div class="row">
        <div class="col-lg-4">
            <h1>Projects</h1>
        </div>
        <div class="col-lg-8">
            <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#newProjectModal"> New project </button>
        </div>
    </div>
    <hr>

    <!-- Project types -->
    <div class="btn-group btn-group-lg" role="group" aria-label="Button group for project by type searching" style="margin-bottom: 10px;">
        <a href="/project"> <button type="button" class="btn btn-light"> All </button> </a>
        <a href="/project?type=NEW"> <button type="button" class="btn btn-light"> New projects </button> </a>
        <a href="/project?type=ACCEPTED"> <button type="button" class="btn btn-light"> Accepted projects </button> </a>
        <a href="/project?type=DECLINED"> <button type="button" class="btn btn-light"> Declined projects </button> </a>
        <a href="/project?type=ARCHIVED"> <button type="button" class="btn btn-light"> Archived projects </button> </a>
    </div>

    <@m.modal "newProjectModal" "project">
        <form method="post" action="/project" id="project">
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

    <!-- List of projects -->
    <div class="row">
        <#list projects as project>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="card my-2 shadow" style="width: 18rem;">
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
                                        <a href="/intensive/${intensive.id}">${intensive.name}</a>
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
            No projects right now zzz
        </#list>
    </div>
</@p.page>