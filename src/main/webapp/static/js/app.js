document.addEventListener('DOMContentLoaded', () => {
    // Grab context‐path for constructing URLs
    const ctx = document.body.getAttribute('data-context-path') || '';

    // ===== Debug Tools =====
    const repairBtn = document.querySelector('#debug-tools button:nth-child(1)');
    if (repairBtn) {
        repairBtn.addEventListener('click', () => {
            window.location.href = `${ctx}/app/admin?_method=reset`;
        });
    }

    const resetBtn = document.querySelector('#debug-tools button:nth-child(2)');
    if (resetBtn) {
        resetBtn.addEventListener('click', () => {
            if (confirm('WARNING: This will delete ALL user data. Continue?')) {
                window.location.href = `${ctx}/app/admin?_method=reset`;
            }
        });
    }
});
