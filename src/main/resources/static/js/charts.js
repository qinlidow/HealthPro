/**
 * 图表组件库 - 使用Canvas绘制
 */

const charts = {
    /**
     * 绘制折线图
     */
    drawLineChart(canvas, data, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const padding = options.padding || { top: 30, right: 30, bottom: 50, left: 60 };
        
        // 清空画布
        ctx.clearRect(0, 0, width, height);
        
        // 计算绘图区域
        const chartWidth = width - padding.left - padding.right;
        const chartHeight = height - padding.top - padding.bottom;
        
        // 获取数据范围
        const values = data.datasets.flatMap(d => d.data);
        const minValue = options.minY ?? Math.min(...values) * 0.9;
        const maxValue = options.maxY ?? Math.max(...values) * 1.1;
        
        // 绘制背景网格
        ctx.strokeStyle = '#E0E0E0';
        ctx.lineWidth = 1;
        
        // 横向网格线
        const ySteps = 5;
        for (let i = 0; i <= ySteps; i++) {
            const y = padding.top + (chartHeight / ySteps) * i;
            ctx.beginPath();
            ctx.moveTo(padding.left, y);
            ctx.lineTo(width - padding.right, y);
            ctx.stroke();
            
            // Y轴标签
            const value = maxValue - ((maxValue - minValue) / ySteps) * i;
            ctx.fillStyle = '#757575';
            ctx.font = '12px sans-serif';
            ctx.textAlign = 'right';
            ctx.fillText(value.toFixed(1), padding.left - 10, y + 4);
        }
        
        // X轴标签
        const xStep = chartWidth / (data.labels.length - 1);
        data.labels.forEach((label, i) => {
            const x = padding.left + xStep * i;
            ctx.fillStyle = '#757575';
            ctx.font = '12px sans-serif';
            ctx.textAlign = 'center';
            ctx.fillText(label, x, height - padding.bottom + 20);
        });
        
        // 绘制数据线
        data.datasets.forEach((dataset, datasetIndex) => {
            ctx.strokeStyle = dataset.color || this.getColor(datasetIndex);
            ctx.lineWidth = 2;
            ctx.beginPath();
            
            dataset.data.forEach((value, i) => {
                const x = padding.left + xStep * i;
                const y = padding.top + chartHeight - ((value - minValue) / (maxValue - minValue)) * chartHeight;
                
                if (i === 0) {
                    ctx.moveTo(x, y);
                } else {
                    ctx.lineTo(x, y);
                }
            });
            
            ctx.stroke();
            
            // 绘制数据点
            dataset.data.forEach((value, i) => {
                const x = padding.left + xStep * i;
                const y = padding.top + chartHeight - ((value - minValue) / (maxValue - minValue)) * chartHeight;
                
                ctx.fillStyle = dataset.color || this.getColor(datasetIndex);
                ctx.beginPath();
                ctx.arc(x, y, 4, 0, Math.PI * 2);
                ctx.fill();
            });
            
            // 绘制填充区域
            if (options.fill) {
                ctx.fillStyle = (dataset.color || this.getColor(datasetIndex)) + '20';
                ctx.beginPath();
                
                dataset.data.forEach((value, i) => {
                    const x = padding.left + xStep * i;
                    const y = padding.top + chartHeight - ((value - minValue) / (maxValue - minValue)) * chartHeight;
                    
                    if (i === 0) {
                        ctx.moveTo(x, y);
                    } else {
                        ctx.lineTo(x, y);
                    }
                });
                
                ctx.lineTo(padding.left + xStep * (dataset.data.length - 1), padding.top + chartHeight);
                ctx.lineTo(padding.left, padding.top + chartHeight);
                ctx.closePath();
                ctx.fill();
            }
        });
        
        // 绘制图例
        if (options.showLegend !== false) {
            const legendY = 10;
            let legendX = padding.left;
            
            data.datasets.forEach((dataset, i) => {
                ctx.fillStyle = dataset.color || this.getColor(i);
                ctx.fillRect(legendX, legendY, 20, 3);
                
                ctx.fillStyle = '#212121';
                ctx.font = '12px sans-serif';
                ctx.textAlign = 'left';
                ctx.fillText(dataset.label || `数据${i + 1}`, legendX + 25, legendY + 4);
                
                legendX += 100;
            });
        }
    },
    
    /**
     * 绘制柱状图
     */
    drawBarChart(canvas, data, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const padding = options.padding || { top: 30, right: 30, bottom: 50, left: 60 };
        
        ctx.clearRect(0, 0, width, height);
        
        const chartWidth = width - padding.left - padding.right;
        const chartHeight = height - padding.top - padding.bottom;
        
        const values = data.datasets.flatMap(d => d.data);
        const maxValue = options.maxY ?? Math.max(...values) * 1.2;
        
        // 绘制网格
        ctx.strokeStyle = '#E0E0E0';
        ctx.lineWidth = 1;
        
        const ySteps = 5;
        for (let i = 0; i <= ySteps; i++) {
            const y = padding.top + (chartHeight / ySteps) * i;
            ctx.beginPath();
            ctx.moveTo(padding.left, y);
            ctx.lineTo(width - padding.right, y);
            ctx.stroke();
            
            const value = maxValue - (maxValue / ySteps) * i;
            ctx.fillStyle = '#757575';
            ctx.font = '12px sans-serif';
            ctx.textAlign = 'right';
            ctx.fillText(value.toFixed(0), padding.left - 10, y + 4);
        }
        
        // 绘制柱状
        const barWidth = chartWidth / data.labels.length * 0.6;
        const barGap = chartWidth / data.labels.length * 0.4;
        
        data.labels.forEach((label, i) => {
            const x = padding.left + (barWidth + barGap) * i + barGap / 2;
            
            data.datasets.forEach((dataset, datasetIndex) => {
                const value = dataset.data[i];
                const barHeight = (value / maxValue) * chartHeight;
                const y = padding.top + chartHeight - barHeight;
                
                // 绘制柱子
                const gradient = ctx.createLinearGradient(x, y, x, padding.top + chartHeight);
                const color = dataset.color || this.getColor(datasetIndex);
                gradient.addColorStop(0, color);
                gradient.addColorStop(1, color + '80');
                
                ctx.fillStyle = gradient;
                ctx.beginPath();
                ctx.roundRect(x + datasetIndex * (barWidth / data.datasets.length), y, barWidth / data.datasets.length - 2, barHeight, 4);
                ctx.fill();
            });
            
            // X轴标签
            ctx.fillStyle = '#757575';
            ctx.font = '12px sans-serif';
            ctx.textAlign = 'center';
            ctx.fillText(label, x + barWidth / 2, height - padding.bottom + 20);
        });
    },
    
    /**
     * 绘制饼图
     */
    drawPieChart(canvas, data, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const centerX = width / 2;
        const centerY = height / 2;
        const radius = Math.min(width, height) / 2 - 40;
        
        ctx.clearRect(0, 0, width, height);
        
        const total = data.data.reduce((sum, val) => sum + val, 0);
        let startAngle = -Math.PI / 2;
        
        data.data.forEach((value, i) => {
            const sliceAngle = (value / total) * Math.PI * 2;
            const color = data.colors?.[i] || this.getColor(i);
            
            // 绘制扇形
            ctx.fillStyle = color;
            ctx.beginPath();
            ctx.moveTo(centerX, centerY);
            ctx.arc(centerX, centerY, radius, startAngle, startAngle + sliceAngle);
            ctx.closePath();
            ctx.fill();
            
            // 绘制标签
            const labelAngle = startAngle + sliceAngle / 2;
            const labelX = centerX + Math.cos(labelAngle) * (radius * 0.7);
            const labelY = centerY + Math.sin(labelAngle) * (radius * 0.7);
            
            ctx.fillStyle = '#FFFFFF';
            ctx.font = 'bold 14px sans-serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(`${((value / total) * 100).toFixed(1)}%`, labelX, labelY);
            
            startAngle += sliceAngle;
        });
        
        // 绘制图例
        if (options.showLegend !== false) {
            const legendStartY = height - 30;
            let legendX = 20;
            
            data.labels.forEach((label, i) => {
                ctx.fillStyle = data.colors?.[i] || this.getColor(i);
                ctx.fillRect(legendX, legendStartY, 12, 12);
                
                ctx.fillStyle = '#212121';
                ctx.font = '12px sans-serif';
                ctx.textAlign = 'left';
                ctx.fillText(label, legendX + 16, legendStartY + 10);
                
                legendX += 100;
            });
        }
    },
    
    /**
     * 绘制环形图
     */
    drawDoughnutChart(canvas, data, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const centerX = width / 2;
        const centerY = height / 2 - 20;
        const outerRadius = Math.min(width, height) / 2 - 40;
        const innerRadius = outerRadius * 0.6;
        
        ctx.clearRect(0, 0, width, height);
        
        const total = data.data.reduce((sum, val) => sum + val, 0);
        let startAngle = -Math.PI / 2;
        
        data.data.forEach((value, i) => {
            const sliceAngle = (value / total) * Math.PI * 2;
            const color = data.colors?.[i] || this.getColor(i);
            
            ctx.fillStyle = color;
            ctx.beginPath();
            ctx.arc(centerX, centerY, outerRadius, startAngle, startAngle + sliceAngle);
            ctx.arc(centerX, centerY, innerRadius, startAngle + sliceAngle, startAngle, true);
            ctx.closePath();
            ctx.fill();
            
            startAngle += sliceAngle;
        });
        
        // 中心文字
        if (options.centerText) {
            ctx.fillStyle = '#212121';
            ctx.font = 'bold 24px sans-serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(options.centerText, centerX, centerY - 10);
            
            if (options.centerSubtext) {
                ctx.font = '14px sans-serif';
                ctx.fillStyle = '#757575';
                ctx.fillText(options.centerSubtext, centerX, centerY + 15);
            }
        }
    },
    
    /**
     * 绘制面积图
     */
    drawAreaChart(canvas, data, options = {}) {
        this.drawLineChart(canvas, data, { ...options, fill: true });
    },
    
    /**
     * 绘制雷达图
     */
    drawRadarChart(canvas, data, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const centerX = width / 2;
        const centerY = height / 2;
        const radius = Math.min(width, height) / 2 - 60;
        
        ctx.clearRect(0, 0, width, height);
        
        const numAxes = data.labels.length;
        const angleStep = (Math.PI * 2) / numAxes;
        
        // 绘制网格
        for (let level = 1; level <= 5; level++) {
            const levelRadius = (radius / 5) * level;
            ctx.strokeStyle = '#E0E0E0';
            ctx.lineWidth = 1;
            ctx.beginPath();
            
            for (let i = 0; i <= numAxes; i++) {
                const angle = angleStep * i - Math.PI / 2;
                const x = centerX + Math.cos(angle) * levelRadius;
                const y = centerY + Math.sin(angle) * levelRadius;
                
                if (i === 0) {
                    ctx.moveTo(x, y);
                } else {
                    ctx.lineTo(x, y);
                }
            }
            
            ctx.stroke();
        }
        
        // 绘制轴线和标签
        data.labels.forEach((label, i) => {
            const angle = angleStep * i - Math.PI / 2;
            const x = centerX + Math.cos(angle) * radius;
            const y = centerY + Math.sin(angle) * radius;
            
            ctx.strokeStyle = '#E0E0E0';
            ctx.beginPath();
            ctx.moveTo(centerX, centerY);
            ctx.lineTo(x, y);
            ctx.stroke();
            
            // 标签
            const labelX = centerX + Math.cos(angle) * (radius + 20);
            const labelY = centerY + Math.sin(angle) * (radius + 20);
            
            ctx.fillStyle = '#757575';
            ctx.font = '12px sans-serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(label, labelX, labelY);
        });
        
        // 绘制数据
        data.datasets.forEach((dataset, datasetIndex) => {
            const color = dataset.color || this.getColor(datasetIndex);
            
            ctx.fillStyle = color + '40';
            ctx.strokeStyle = color;
            ctx.lineWidth = 2;
            ctx.beginPath();
            
            dataset.data.forEach((value, i) => {
                const angle = angleStep * i - Math.PI / 2;
                const pointRadius = (value / 100) * radius;
                const x = centerX + Math.cos(angle) * pointRadius;
                const y = centerY + Math.sin(angle) * pointRadius;
                
                if (i === 0) {
                    ctx.moveTo(x, y);
                } else {
                    ctx.lineTo(x, y);
                }
            });
            
            ctx.closePath();
            ctx.fill();
            ctx.stroke();
        });
    },
    
    /**
     * 绘制进度环
     */
    drawProgressRing(canvas, value, maxValue, options = {}) {
        const ctx = canvas.getContext('2d');
        const width = canvas.width;
        const height = canvas.height;
        const centerX = width / 2;
        const centerY = height / 2;
        const radius = Math.min(width, height) / 2 - 10;
        const lineWidth = options.lineWidth || 10;
        
        ctx.clearRect(0, 0, width, height);
        
        const percentage = value / maxValue;
        const startAngle = -Math.PI / 2;
        const endAngle = startAngle + Math.PI * 2 * percentage;
        
        // 背景环
        ctx.strokeStyle = '#E0E0E0';
        ctx.lineWidth = lineWidth;
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius - lineWidth / 2, 0, Math.PI * 2);
        ctx.stroke();
        
        // 进度环
        const gradient = ctx.createLinearGradient(0, 0, width, height);
        gradient.addColorStop(0, options.color || '#4CAF50');
        gradient.addColorStop(1, options.colorEnd || '#8BC34A');
        
        ctx.strokeStyle = gradient;
        ctx.lineCap = 'round';
        ctx.beginPath();
        ctx.arc(centerX, centerY, radius - lineWidth / 2, startAngle, endAngle);
        ctx.stroke();
        
        // 中心文字
        ctx.fillStyle = '#212121';
        ctx.font = 'bold 24px sans-serif';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText(`${Math.round(percentage * 100)}%`, centerX, centerY);
    },
    
    /**
     * 获取颜色
     */
    getColor(index) {
        const colors = [
            '#4CAF50', '#2196F3', '#FF9800', '#9C27B0', '#F44336',
            '#00BCD4', '#FF5722', '#795548', '#607D8B', '#E91E63'
        ];
        return colors[index % colors.length];
    },
    
    /**
     * 创建图表容器
     */
    createChartContainer(containerId, title, options = {}) {
        const container = document.getElementById(containerId);
        if (!container) return null;
        
        container.innerHTML = `
            <div class="chart-container">
                <div class="chart-header">
                    <h3 class="chart-title">${title}</h3>
                    ${options.actions ? `<div class="chart-actions">${options.actions}</div>` : ''}
                </div>
                <div class="chart-body">
                    <canvas id="${containerId}-canvas" width="${options.width || 600}" height="${options.height || 300}"></canvas>
                </div>
            </div>
        `;
        
        return document.getElementById(`${containerId}-canvas`);
    }
};

// 导出图表组件
window.charts = charts;
