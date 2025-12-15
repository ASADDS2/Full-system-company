const QuotationView = (() => {
    // DOM Elements - Lazy getters or just query when needed to ensure DOM is ready? 
    // Since script is loaded at bottom or we init on DOMContentLoaded, we can cache selectors or just query.
    // Let's use a simple helper to get elements.

    const elem = (id) => document.getElementById(id);
    const qs = (sel) => document.querySelector(sel);

    // State for UI to track current edit ID if needed, 
    // or Controller can pass it. Let's keep View stateless if possible over "this", 
    // but we need to toggle buttons based on state. 
    // We can expose a "populateForm" that sets up the UI state.

    function getFormData() {
        return {
            clientName: elem('clientName').value,
            serviceType: elem('serviceType').value,
            hours: parseInt(elem('hours').value),
            isRecurringClient: elem('recurringClient').checked,
        };
    }

    function bindSubmit(handler) {
        elem('quotationForm').addEventListener('submit', event => {
            event.preventDefault();
            handler(getFormData());
        });
    }

    function bindReset(handler) {
        elem('resetBtn').addEventListener('click', () => {
            resetForm();
            handler();
        });
    }

    function bindRefresh(handler) {
        elem('refreshBtn').addEventListener('click', () => handler());
    }

    function bindActions(editHandler, deleteHandler) {
        elem('quotationsBody').addEventListener('click', event => {
            const btn = event.target.closest('.btn-icon');
            if (!btn) return;

            const id = btn.dataset.id;
            if (btn.classList.contains('btn-delete')) {
                deleteHandler(id);
            } else if (btn.classList.contains('btn-edit')) {
                editHandler(id);
            }
        });
    }

    function showLoading(isLoading) {
        const spinner = qs('.loading-spinner');
        const btnText = qs('.btn-text');
        const submitBtn = elem('calculateBtn');

        if (isLoading) {
            spinner.classList.remove('hidden');
            btnText.classList.add('hidden');
            submitBtn.disabled = true;
        } else {
            spinner.classList.add('hidden');
            btnText.classList.remove('hidden');
            submitBtn.disabled = false;
        }
    }

    function showError(message) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: message,
            background: '#1e293b',
            color: '#f8fafc',
            confirmButtonColor: '#6366f1'
        });
    }

    function showSuccess(message) {
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: message,
            timer: 2000,
            showConfirmButton: false,
            background: '#1e293b',
            color: '#f8fafc'
        });
    }

    async function confirmDelete() {
        const result = await Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#ef4444',
            cancelButtonColor: '#334155',
            confirmButtonText: 'Yes, delete it!',
            background: '#1e293b',
            color: '#f8fafc'
        });
        return result.isConfirmed;
    }

    function _formatMoney(amount) {
        return new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0
        }).format(amount);
    }

    function showResult(quotation) {
        elem('resClient').textContent = quotation.clientName;
        elem('resHours').textContent = quotation.hours;
        elem('serviceBadge').textContent = quotation.serviceType.replace('_', ' ');
        elem('resSubtotal').textContent = _formatMoney(quotation.subtotal);
        elem('resTotal').textContent = _formatMoney(quotation.totalAmount);

        const rowDiscount = elem('rowDiscount');
        if (quotation.discountAmount > 0) {
            elem('resDiscount').textContent = `-${_formatMoney(quotation.discountAmount)}`;
            rowDiscount.classList.remove('hidden');
        } else {
            rowDiscount.classList.add('hidden');
        }

        const rowSurcharge = elem('rowSurcharge');
        if (quotation.surchargeAmount > 0) {
            elem('resSurcharge').textContent = `+${_formatMoney(quotation.surchargeAmount)}`;
            rowSurcharge.classList.remove('hidden');
        } else {
            rowSurcharge.classList.add('hidden');
        }

        const resultCard = elem('resultCard');
        resultCard.classList.remove('hidden');
        qs('.main-grid').classList.add('has-results'); // Activate split layout
        resultCard.scrollIntoView({ behavior: 'smooth' });
    }

    function renderTable(quotations) {
        const tbody = elem('quotationsBody');
        tbody.innerHTML = '';

        if (quotations.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" style="text-align:center">No quotations found</td></tr>';
            return;
        }

        quotations.forEach(q => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><strong>${q.clientName}</strong><br><small>${q.isRecurringClient ? 'Recurring' : 'New'}</small></td>
                <td>${q.serviceType.replace('_', ' ')}<br><small>${q.hours} hours</small></td>
                <td><strong>${_formatMoney(q.totalAmount)}</strong></td>
                <td class="actions-cell">
                    <button class="btn-icon btn-edit" data-id="${q.id}" title="Edit">✎</button>
                    <button class="btn-icon btn-delete" data-id="${q.id}" title="Delete">🗑</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    }

    function populateForm(quotation) {
        elem('clientName').value = quotation.clientName;
        elem('serviceType').value = quotation.serviceType;
        elem('hours').value = quotation.hours;
        elem('recurringClient').checked = quotation.isRecurringClient;

        qs('.btn-text').textContent = 'Update Quotation';
        elem('calculateBtn').classList.add('btn-warning');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function resetForm() {
        elem('quotationForm').reset();
        qs('.btn-text').textContent = 'Calculate Quotation';
        elem('calculateBtn').classList.remove('btn-warning');
        elem('resultCard').classList.add('hidden');
        qs('.main-grid').classList.remove('has-results'); // Deactivate split layout
        elem('errorMsg').classList.add('hidden');
    }

    return {
        bindSubmit,
        bindReset,
        bindRefresh,
        bindActions,
        showLoading,
        showError,
        showSuccess,
        confirmDelete,
        showResult,
        renderTable,
        populateForm,
        resetForm
    };
})();
