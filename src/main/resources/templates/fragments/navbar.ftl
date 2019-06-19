<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-info mb-3">
    <!-- Main page -->
    <a class="navbar-brand text-dark" href="/" style="font-weight: bold">Home</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <!-- Intensives list -->
            <li class="nav-item">
                <a class="nav-link text-dark" href="/intensive">Intensives</a>
            </li>
            <!-- Projects list -->
            <li class="nav-item">
                <a class="nav-link text-dark" href="/project">Projects</a>
            </li>
            <!-- Users list -->
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link text-dark" href="/user">Users</a>
                </li>
            </#if>
        </ul>

        <!-- Profile -->
        <div class="navbar-text mr-3 text-dark">
            <a href="/profile">Profile</a>
        </div>

        <!-- Logout -->
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Logout</button>
        </form>
    </div>
</nav>