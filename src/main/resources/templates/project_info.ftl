<#import "fragments/page.ftl" as p>

<@p.page "Intensives list">
    <!-- Description of intensive -->
    <h5>${project.name}</h5>
    <p>${project.description}</p>
    <p>Created: ${project.date_created}</p>
    <p>Curator: ${project.creator.username}</p>

    <!-- Change description -->
    <#if user_now.isModerator() || user_now.id == project.creator.id>
        <@p.collapse "Edit" "edit">
            <form method="post" action="/project/${project.id}/edit">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="text" placeholder="Name of project" name="name" class="form-control my-2"/>
                <input type="text" placeholder="Description of project" name="description" class="form-control my-2"/>
                <button type="submit" class="btn btn-primary mt-2">Update</button>
            </form>
        </@p.collapse>
    </#if>

    <!-- Add users -->
    <#if user_now.isCurator()>
        <@p.collapse "Add participant" "add">
            <form method="post" action="/project/${project.id}/add">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label for="selectUser">Select new participant</label>
                <select id="selectUser" name="username">
                    <#list all_users as user>
                        <option selected>${user.username}</option>
                    </#list>
                </select>
                <button type="submit" class="btn btn-primary mt-2">Add</button>
            </form>
        </@p.collapse>
    </#if>

    <!-- Team -->
    <#list project.team as user>
        <div>
            ${user.username}
        </div>
    <#else>
        <h3>No participants yet</h3>
    </#list>
</@p.page>