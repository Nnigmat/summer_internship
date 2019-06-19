<#import "fragments/page.ftl" as p>

<@p.page "Intensives list">
    <!-- New intensive -->
    <#if user_now.isCurator()>
        <@p.collapse "New intensive" "intensive">
            <form method="post" action="/intensive">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="text" placeholder="Name of intensive" name="name" class="form-control my-2"/>
                <input type="text" placeholder="Description of intensive" name="description" class="form-control my-2"/>
                <input type="text" placeholder="Date of start" name="date_start" class="form-control my-2"/>
                <input type="text" placeholder="Date of end" name="date_end" class="form-control my-2"/>
                <button type="submit" class="btn btn-primary mt-2">Create</button>
            </form>
        </@p.collapse>
    </#if>

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