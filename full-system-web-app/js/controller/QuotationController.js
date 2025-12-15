const QuotationController = (() => {
    // Dependencies
    const Model = QuotationModel;
    const View = QuotationView;

    // State
    let currentEditId = null;

    async function loadQuotations() {
        try {
            const quotations = await Model.getAllQuotations();
            View.renderTable(quotations);
        } catch (error) {
            console.error('Failed to load quotations', error);
        }
    }

    async function handleSubmit(data) {
        if (!validate(data)) return;

        View.showLoading(true);

        try {
            let result;
            if (currentEditId) {
                // Update
                result = await Model.updateQuotation(currentEditId, data);
                View.showSuccess('Quotation Updated Successfully!'); // Feedback
            } else {
                // Create
                result = await Model.createQuotation(data);
                View.showSuccess('Quotation Created Successfully!');
            }

            View.showResult(result);
            loadQuotations(); // Refresh list

            if (currentEditId) {
                setTimeout(() => handleReset(), 2000);
            }

        } catch (error) {
            View.showError(error.message || 'An unexpected error occurred');
        } finally {
            View.showLoading(false);
        }
    }

    function handleEdit(id) {
        Model.getAllQuotations().then(quotations => {
            // Soft eq for string ID from dataset vs number from API
            const quotation = quotations.find(q => q.id == id);
            if (quotation) {
                currentEditId = quotation.id;
                View.populateForm(quotation);
            }
        });
    }

    async function handleDelete(id) {
        const confirmed = await View.confirmDelete();
        if (!confirmed) return;

        try {
            await Model.deleteQuotation(id);
            View.showSuccess('Quotation Deleted Successfully');
            loadQuotations();
        } catch (error) {
            View.showError(error.message || 'Error deleting quotation');
        }
    }

    function handleReset() {
        currentEditId = null;
        View.resetForm();
    }

    function validate(data) {
        if (!data.clientName || data.clientName.trim() === '') {
            View.showError('Client Name is required');
            return false;
        }
        if (!data.serviceType) {
            View.showError('Please select a Service Type');
            return false;
        }
        if (data.hours <= 0) {
            View.showError('Hours must be greater than 0');
            return false;
        }
        return true;
    }

    function init() {
        View.bindSubmit(handleSubmit);
        View.bindReset(handleReset);
        View.bindRefresh(loadQuotations);
        View.bindActions(handleEdit, handleDelete);

        // Initial Load
        loadQuotations();
    }

    return {
        init
    };
})();
