/**
 * 工具函数库
 */

const utils = {
    /**
     * 格式化日期
     */
    formatDate(date, format = 'YYYY-MM-DD') {
        const d = new Date(date);
        const year = d.getFullYear();
        const month = String(d.getMonth() + 1).padStart(2, '0');
        const day = String(d.getDate()).padStart(2, '0');
        const hours = String(d.getHours()).padStart(2, '0');
        const minutes = String(d.getMinutes()).padStart(2, '0');
        const seconds = String(d.getSeconds()).padStart(2, '0');
        
        return format
            .replace('YYYY', year)
            .replace('MM', month)
            .replace('DD', day)
            .replace('HH', hours)
            .replace('mm', minutes)
            .replace('ss', seconds);
    },
    
    /**
     * 获取相对时间
     */
    getRelativeTime(date) {
        const now = new Date();
        const d = new Date(date);
        const diff = now - d;
        
        const minutes = Math.floor(diff / 60000);
        const hours = Math.floor(diff / 3600000);
        const days = Math.floor(diff / 86400000);
        
        if (minutes < 1) return '刚刚';
        if (minutes < 60) return `${minutes}分钟前`;
        if (hours < 24) return `${hours}小时前`;
        if (days < 7) return `${days}天前`;
        return this.formatDate(date, 'MM-DD');
    },
    
    /**
     * 计算BMI
     */
    calculateBMI(weight, height) {
        if (!weight || !height) return 0;
        const heightM = height / 100;
        return (weight / (heightM * heightM)).toFixed(1);
    },
    
    /**
     * 获取BMI状态
     */
    getBMIStatus(bmi) {
        if (bmi < 18.5) return { status: '偏瘦', color: '#2196F3', advice: '建议适当增加营养摄入' };
        if (bmi < 24) return { status: '正常', color: '#4CAF50', advice: '继续保持健康的生活方式' };
        if (bmi < 28) return { status: '偏胖', color: '#FF9800', advice: '建议控制饮食并增加运动' };
        return { status: '肥胖', color: '#F44336', advice: '建议咨询医生制定减重计划' };
    },
    
    /**
     * 获取血压状态
     */
    getBloodPressureStatus(high, low) {
        if (high < 90 || low < 60) return { status: '偏低', color: '#2196F3', advice: '建议适当增加营养，必要时就医' };
        if (high < 120 && low < 80) return { status: '理想', color: '#4CAF50', advice: '血压状态良好' };
        if (high < 140 && low < 90) return { status: '正常', color: '#8BC34A', advice: '血压在正常范围内' };
        if (high < 160 && low < 100) return { status: '偏高', color: '#FF9800', advice: '建议改善生活方式，定期监测' };
        return { status: '高血压', color: '#F44336', advice: '建议及时就医治疗' };
    },
    
    /**
     * 获取血糖状态
     */
    getBloodSugarStatus(sugar, isFasting = true) {
        if (isFasting) {
            if (sugar < 3.9) return { status: '偏低', color: '#2196F3', advice: '建议及时补充糖分' };
            if (sugar < 6.1) return { status: '正常', color: '#4CAF50', advice: '血糖控制良好' };
            if (sugar < 7.0) return { status: '偏高', color: '#FF9800', advice: '建议控制饮食，增加运动' };
            return { status: '过高', color: '#F44336', advice: '建议就医检查' };
        } else {
            if (sugar < 3.9) return { status: '偏低', color: '#2196F3', advice: '建议及时补充糖分' };
            if (sugar < 7.8) return { status: '正常', color: '#4CAF50', advice: '血糖控制良好' };
            if (sugar < 11.1) return { status: '偏高', color: '#FF9800', advice: '建议控制饮食，增加运动' };
            return { status: '过高', color: '#F44336', advice: '建议就医检查' };
        }
    },
    
    /**
     * 获取睡眠质量描述
     */
    getSleepQuality(quality) {
        if (quality >= 90) return { status: '优秀', color: '#4CAF50', emoji: '😴' };
        if (quality >= 80) return { status: '良好', color: '#8BC34A', emoji: '😊' };
        if (quality >= 70) return { status: '一般', color: '#FF9800', emoji: '😐' };
        return { status: '较差', color: '#F44336', emoji: '😫' };
    },
    
    /**
     * 生成图表颜色
     */
    generateChartColors(count) {
        const colors = [
            '#4CAF50', '#2196F3', '#FF9800', '#9C27B0', '#F44336',
            '#00BCD4', '#FF5722', '#795548', '#607D8B', '#E91E63'
        ];
        return colors.slice(0, count);
    },
    
    /**
     * 防抖函数
     */
    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },
    
    /**
     * 节流函数
     */
    throttle(func, limit) {
        let inThrottle;
        return function(...args) {
            if (!inThrottle) {
                func.apply(this, args);
                inThrottle = true;
                setTimeout(() => inThrottle = false, limit);
            }
        };
    },
    
    /**
     * 深拷贝
     */
    deepClone(obj) {
        if (obj === null || typeof obj !== 'object') return obj;
        if (obj instanceof Date) return new Date(obj.getTime());
        if (obj instanceof Array) return obj.map(item => this.deepClone(item));
        
        const clonedObj = {};
        for (const key in obj) {
            if (obj.hasOwnProperty(key)) {
                clonedObj[key] = this.deepClone(obj[key]);
            }
        }
        return clonedObj;
    },
    
    /**
     * 本地存储封装
     */
    storage: {
        get(key) {
            try {
                const value = localStorage.getItem(key);
                return value ? JSON.parse(value) : null;
            } catch {
                return null;
            }
        },
        set(key, value) {
            try {
                localStorage.setItem(key, JSON.stringify(value));
                return true;
            } catch {
                return false;
            }
        },
        remove(key) {
            localStorage.removeItem(key);
        },
        clear() {
            localStorage.clear();
        }
    },
    
    /**
     * 显示提示消息
     */
    showToast(message, type = 'info', duration = 3000) {
        const container = document.querySelector('.toast-container') || this.createToastContainer();
        
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        
        const icons = {
            success: '✓',
            error: '✕',
            warning: '⚠',
            info: 'ℹ'
        };
        
        toast.innerHTML = `
            <span class="toast-icon">${icons[type]}</span>
            <div class="toast-content">
                <div class="toast-message">${message}</div>
            </div>
        `;
        
        container.appendChild(toast);
        
        setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateX(100%)';
            setTimeout(() => toast.remove(), 300);
        }, duration);
    },
    
    createToastContainer() {
        const container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
        return container;
    },
    
    /**
     * 显示加载状态
     */
    showLoading(element) {
        const loader = document.createElement('div');
        loader.className = 'loading-overlay';
        loader.innerHTML = '<div class="loading"></div>';
        element.appendChild(loader);
        return loader;
    },
    
    hideLoading(loader) {
        if (loader) loader.remove();
    },
    
    /**
     * 确认对话框
     */
    confirm(message) {
        return new Promise((resolve) => {
            const overlay = document.createElement('div');
            overlay.className = 'modal-overlay active';
            overlay.innerHTML = `
                <div class="modal" style="max-width: 400px;">
                    <div class="modal-header">
                        <h3 class="modal-title">确认</h3>
                    </div>
                    <div class="modal-body">
                        <p>${message}</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-outline" onclick="this.closest('.modal-overlay').remove(); window._confirmResolve(false);">取消</button>
                        <button class="btn btn-primary" onclick="this.closest('.modal-overlay').remove(); window._confirmResolve(true);">确定</button>
                    </div>
                </div>
            `;
            document.body.appendChild(overlay);
            window._confirmResolve = resolve;
        });
    },
    
    /**
     * 数据预测 - 简单线性回归
     */
    predictLinear(data, daysAhead = 7) {
        if (data.length < 2) return null;
        
        const n = data.length;
        let sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        
        data.forEach((value, index) => {
            sumX += index;
            sumY += value;
            sumXY += index * value;
            sumX2 += index * index;
        });
        
        const slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        const intercept = (sumY - slope * sumX) / n;
        
        const lastX = n - 1;
        const predictedValue = slope * (lastX + daysAhead) + intercept;
        
        return {
            value: Math.round(predictedValue * 10) / 10,
            trend: slope > 0 ? 'up' : slope < 0 ? 'down' : 'stable',
            slope: Math.round(slope * 100) / 100
        };
    },
    
    /**
     * 生成日期范围
     */
    generateDateRange(startDate, days) {
        const dates = [];
        const start = new Date(startDate);
        
        for (let i = 0; i < days; i++) {
            const date = new Date(start);
            date.setDate(date.getDate() + i);
            dates.push(this.formatDate(date));
        }
        
        return dates;
    },
    
    /**
     * 获取本周日期范围
     */
    getWeekRange() {
        const now = new Date();
        const dayOfWeek = now.getDay();
        const start = new Date(now);
        start.setDate(now.getDate() - dayOfWeek);
        
        return {
            start: this.formatDate(start),
            end: this.formatDate(now)
        };
    },
    
    /**
     * 获取本月日期范围
     */
    getMonthRange() {
        const now = new Date();
        const start = new Date(now.getFullYear(), now.getMonth(), 1);
        const end = new Date(now.getFullYear(), now.getMonth() + 1, 0);
        
        return {
            start: this.formatDate(start),
            end: this.formatDate(end)
        };
    },
    
    /**
     * 计算百分比变化
     */
    calculatePercentChange(current, previous) {
        if (previous === 0) return 0;
        return ((current - previous) / previous * 100).toFixed(1);
    },
    
    /**
     * 格式化数字
     */
    formatNumber(num, decimals = 0) {
        return Number(num).toFixed(decimals);
    },
    
    /**
     * 获取随机颜色
     */
    getRandomColor() {
        const colors = ['#4CAF50', '#2196F3', '#FF9800', '#9C27B0', '#F44336', '#00BCD4'];
        return colors[Math.floor(Math.random() * colors.length)];
    }
};

// 导出工具函数
window.utils = utils;
