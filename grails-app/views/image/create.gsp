<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'image.label', default: 'Image')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-image" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-image" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.image}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.image}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm action="save">
                <fieldset class="form">
                    <input type="file" name="file"/>
                </fieldset>
                <fieldset class="buttons">
                    <input type="hidden" value="${request.getHeader('referer') }" name="lastVisited" />
                    <input type="hidden" name="zport.id" value="${params.zport?.id}" />
                    <input type="hidden" name="room.id" value="${params.room?.id}" />
                    <input type="hidden" name="folder" value="${params.zport? 'zport' : 'room'}" />
                    <input type="hidden" name="name" value="test" />
                    <g:submitButton name="create" class="save" value="Upload" />
                </fieldset>
            </g:uploadForm>
%{--            <g:form action="save">
                <fieldset class="form">
                    <f:all bean="image"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>--}%
        </div>
    </body>
</html>
