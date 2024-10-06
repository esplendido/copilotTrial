$(document).ready(function() {
    function updateHeaderFields() {
        var type = $('#type').val();
        var headersContainer = $('#headers');
        headersContainer.empty();
        var headerCount = type === 'SOAP' ? 2 : 5;
        for (var i = 0; i < headerCount; i++) {
            headersContainer.append('<div class="mb-2"><input type="text" class="form-control" placeholder="Header ' + (i + 1) + '"></div>');
        }
    }

    $('#type').change(function() {
        updateHeaderFields();
    });

    $('#addHeader').click(function() {
        $('#headers').append('<div class="mb-2"><input type="text" class="form-control" placeholder="Header"></div>');
    });

    $('#removeHeader').click(function() {
        $('#headers div:last-child').remove();
    });

    $('#execute').click(function() {
        var method = $('#method').val();
        var url = $('#url').val();
        var headers = {};
        $('#headers input').each(function() {
            var header = $(this).val();
            if (header) {
                headers[header] = header;
            }
        });
        var body = $('#body').val();
        var startTime = new Date();
        $('#startTime').val(startTime.toISOString().replace('T', ' ').replace('Z', ''));

        $.ajax({
            url: url,
            method: method,
            headers: headers,
            data: body,
            success: function(response, textStatus, xhr) {
                var endTime = new Date();
                $('#endTime').val(endTime.toISOString().replace('T', ' ').replace('Z', ''));
                var executionTime = (endTime - startTime) / 1000;
                $('#executionTime').val(executionTime.toFixed(3));
                $('#responseHeaders').val(JSON.stringify(xhr.getAllResponseHeaders(), null, 2));
                $('#responseBody').val(JSON.stringify(response, null, 2));
            },
            error: function(xhr, textStatus, errorThrown) {
                alert('Error: ' + errorThrown);
            }
        });
    });

    updateHeaderFields();
});
