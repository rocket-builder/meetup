var editor = new MediumEditor('.editable', {
    /* These are the default options for the editor,
        if nothing is passed this is what is used */
    activeButtonClass: 'medium-editor-button-active',
    allowMultiParagraphSelection: true,
    buttonLabels: false,
    contentWindow: window,
    delay: 0,
    disableReturn: false,
    disableDoubleReturn: false,
    disableExtraSpaces: false,
    disableEditing: false,
    elementsContainer: false,
    extensions: {},
    ownerDocument: document,
    spellcheck: true,
    targetBlank: true,

    toolbar: {
        /* These are the default options for the toolbar,
           if nothing is passed this is what is used */
        allowMultiParagraphSelection: true,
        buttons: ['h1', 'h2', 'bold', 'italic', 'quote', 'pre', 'unorderedlist','orderedlist', 'underline', 'anchor'],
        diffLeft: 0,
        diffTop: -10,
        firstButtonClass: 'medium-editor-button-first',
        lastButtonClass: 'medium-editor-button-last',
        relativeContainer: null,
        standardizeSelectionStart: false,
        static: false,
        buttonLabels: 'fontawesome',
        /* options which only apply when static is true */
        align: 'center',
        sticky: true,
        updateOnEmptySelection: false,
        anchorPreview: {
            /* These are the default options for anchor preview,
               if nothing is passed this is what it used */
            hideDelay: 500,
            previewValueSelector: 'a'
        }
    },

    placeholder: {
        /* This example includes the default options for placeholder,
           if nothing is passed this is what it used */
        text: 'Write your article here',
        hideOnClick: true
    }
});
