const QuotationModel = (() => {
    const apiUrl = 'http://localhost:8080/api/quotations';

    async function _request(url, method, data = null) {
        try {
            const options = {
                method,
                headers: { 'Content-Type': 'application/json' }
            };

            if (data) options.body = JSON.stringify(data);

            const response = await fetch(url, options);

            // Handle empty responses (like DELETE)
            if (response.status === 204) return null;

            const result = await response.json();

            if (!response.ok) {
                throw new Error(result.message || `Error ${method}ing quotation`);
            }

            return result.data;
        } catch (error) {
            console.error(`API Error (${method}):`, error);
            throw error;
        }
    }

    return {
        createQuotation: (data) => _request(apiUrl, 'POST', data),
        getAllQuotations: () => _request(apiUrl, 'GET'),
        updateQuotation: (id, data) => _request(`${apiUrl}/${id}`, 'PUT', data),
        deleteQuotation: (id) => _request(`${apiUrl}/${id}`, 'DELETE')
    };
})();
