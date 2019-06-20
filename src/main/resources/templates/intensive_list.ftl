<#import "fragments/page.ftl" as p>
<#import "fragments/modal.ftl" as m>

<@p.page "Intensives list">
    <div class="row">

        <div class="col-lg-4">
            <h1>Our intensives</h1>
        </div>
        <!-- New intensive -->
        <div class="col-lg-8">
            <#if user_now.isCurator()>
                <button type="button" class="btn btn-primary float-right" data-toggle="modal" data-target="#newIntensiveModal"> New intensive </button>
            </#if>
        </div>
    </div>
    <hr>

    <@m.modal "newIntensiveModal" "intensive" "Create">
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
    </@m.modal>

    <!-- List of intensives -->
    <div class="row">
        <#list intensives as intensive>
            <div class="col-4">
                <div class="card my-2 shadow">
                    <div class="card-body">
                        <a href="/intensive/${intensive.id}" style="color: inherit;"><h5 class="card-title">${intensive.name}</h5></a>
                        <p class="card-text" style="max-height: 100px">
                            ${intensive.description}
                        </p>
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