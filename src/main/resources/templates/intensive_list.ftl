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

    <@m.modal "newIntensiveModal" "intensive" "Create" "Create intensive">
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
                <label for="date_end" class="col-form-label">Date of start (YYYY-MM-DD):</label>
                <textarea class="form-control" name="date_start"></textarea>
            </div>
            <div class="form-group">
                <label for="date_end" class="col-form-label">Date of end (YYYY-MM-DD):</label>
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
                        <a href="/intensive/${intensive.id}" style="color: inherit;"><h5 class="card-title"
                                                                                         id="${intensive.id}list1">
                                <@p.text "${intensive.id}list1" "${intensive.name}"/></h5></a>
                        <p class="card-text" style="max-height: 100px; overflow: hidden; text-overflow: ellipsis;"
                           id="${intensive.id}list2">
                            <@p.text "${intensive.id}list2" "${intensive.description}"/>
                        </p>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"
                                id="${intensive.id}list3"><@p.text "${intensive.id}list3" "Start: ${intensive.date_start}"/></li>
                            <li class="list-group-item"
                                id="${intensive.id}list4"><@p.text "${intensive.id}list4" "End: ${intensive.date_end}"/></li>
                        </ul>
                    </div>
                    <div class="card-footer text-muted" id="${intensive.id}list5">
                        <@p.text "${intensive.id}list5" "Curator: ${intensive.curator.username}"/>
                    </div>
                </div>
            </div>
        <#else>
            No intensives right now zzz
        </#list>
    </div>
</@p.page>