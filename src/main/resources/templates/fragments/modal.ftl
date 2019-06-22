<#macro modal button_id form_id button_text title>
    <div class="modal fade" id="${button_id}" tabindex="-1" role="dialog" aria-labelledby="${button_id}Label" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="${button_id}">${title}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">

                    <#nested>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" value="save" onclick="event.preventDefault();
                            document.getElementById('${form_id}').submit();">${button_text}</button>
                </div>
            </div>
        </div>
    </div>
</#macro>