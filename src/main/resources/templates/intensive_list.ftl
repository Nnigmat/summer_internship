<#import "fragments/page.ftl" as p>

<@p.page "Intensives list">
    <!-- New intensive -->
    <#if user_now.isCurator()>
        <a><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newIntensiveModal"> New intensive </button></a>
    </#if>

    <div class="modal fade" id="newIntensiveModal" tabindex="-1" role="dialog" aria-labelledby="newIntensiveModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="newProjectModalLabel">New message</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="/intensive" id="intensive">
                        <div class="form-group">
                            <label for="name" class="col-form-label">Name of intensive:</label>
                            <input type="text" class="form-control" name="name">
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-form-label">Description:</label>
                            <textarea class="form-control" name="description"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="date_end" class="col-form-label">Date of start:</label>
                            <textarea class="form-control" name="date_start"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="date_end" class="col-form-label">Date of end:</label>
                            <textarea class="form-control" name="date_end"></textarea>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" value="save" onclick="event.preventDefault();document.getElementById('intensive').submit();">Create project</button>
                </div>
            </div>
        </div>
    </div>

    <!-- List of intensives -->
    <div class="row">
        <#list intensives as intensive>
            <div class="col-4">
                <div class="card my-2">
                    <div class="card-body">
                        <h5 class="card-title">${intensive.name}</h5>
                        <p class="card-text">
                            ${intensive.description}
                        </p>
                        <a href="/intensive/${intensive.id}">Info >></a>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Start: ${intensive.date_start}</li>
                            <li class="list-group-item">End: ${intensive.date_end}</li>
                        </ul>
                    </div>
                    <div class="card-footer text-muted">
                        Curator: ${intensive.curator.username}
                    </div>
                </div>
            </div>
        <#else>
            No intensives right now zzz
        </#list>
    </div>
</@p.page>