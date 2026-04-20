/**
 * API配置和请求封装
 * 基于后端已实现的接口
 */

// API基础配置
const API_BASE_URL = '';

// API端点配置
const API_ENDPOINTS = {
    // 用户认证相关（已实现）
    auth: {
        login: '/Login',           // POST - 用户登录
        register: '/Register',      // POST - 用户注册
        logout: '/Logout'           // POST - 用户登出
    },
    
    // 用户信息相关（已实现）
    user: {
        getInfo: '/GetInfo',                    // GET - 获取用户信息
        updateInfo: '/UpdateInfo',              // POST - 更新用户信息
        updatePassword: '/UpdatePassword'       // POST - 更新密码
    },
    
    // 身体数据相关（已实现）
    body: {
        get: '/Body/GetBody',                   // POST - 获取身体数据列表
        add: '/Body/AddBody',                   // POST - 添加身体数据
        update: '/Body/SetBody',                // POST - 更新身体数据 (需要id参数)
        delete: '/Body/DeleteBody'              // DELETE - 删除身体数据 (需要id参数)
    },
    
    // 饮食记录相关
    diet: {
        get: '/Diet/GetDiet',                   // GET - 获取饮食记录
        add: '/Diet/AddDiet',                   // POST - 添加饮食记录
        update: '/Diet/SetDiet',                // POST - 更新饮食记录
        delete: '/Diet/DeleteDiet'              // DELETE - 删除饮食记录
    },
    
    // 睡眠管理相关
    sleep: {
        // get: '/Sleep/GetSleep',
        // add: '/Sleep/AddSleep',
        // update: '/Sleep/SetSleep',
        // delete: '/Sleep/DeleteSleep'
    },
    
    // 健身管理相关
    fitness: {
        // get: '/Fitness/GetFitness',
        // add: '/Fitness/AddFitness',
        // update: '/Fitness/SetFitness',
        // delete: '/Fitness/DeleteFitness'
    },
    
    // 社交广场相关（已实现）
    social: {
        getPosts: '/Social/GetPosts',           // GET - 获取帖子列表
        addPost: '/Social/AddPost',             // POST - 发布帖子
        likePost: '/Social/LikePost',           // POST - 点赞
        commentPost: '/Social/CommentPost',     // POST - 评论
        deletePost: '/Social/DeletePost'        // DELETE - 删除帖子
    }
};

/**
 * HTTP请求封装
 */
class HttpClient {
    constructor() {
        this.baseURL = API_BASE_URL;
    }
    
    /**
     * 获取存储的Token
     */
    getToken() {
        return localStorage.getItem('token');
    }
    
    /**
     * 设置Token
     */
    setToken(token) {
        localStorage.setItem('token', token);
    }
    
    /**
     * 移除Token
     */
    removeToken() {
        localStorage.removeItem('token');
    }
    
    /**
     * 检查是否已登录
     */
    isAuthenticated() {
        return !!this.getToken();
    }
    
    /**
     * 发送请求
     */
    async request(url, options = {}) {
        const token = this.getToken();
        
        const defaultHeaders = {
            'Content-Type': 'application/json'
        };
        
        if (token) {
            defaultHeaders['Authorization'] = `Bearer ${token}`;
        }
        
        const config = {
            ...options,
            headers: {
                ...defaultHeaders,
                ...options.headers
            }
        };
        
        try {
            const response = await fetch(this.baseURL + url, config);
            const data = await response.json();
            
            // 处理响应
            if (data.code === 200) {
                return { success: true, data: data.data, message: data.message };
            } else {
                return { success: false, data: null, message: data.message };
            }
        } catch (error) {
            console.error('API请求错误:', error);
            return { success: false, data: null, message: '网络请求失败，请检查后端服务是否启动' };
        }
    }
    
    /**
     * GET请求
     */
    async get(url) {
        return this.request(url, { method: 'GET' });
    }
    
    /**
     * POST请求
     */
    async post(url, data = {}) {
        return this.request(url, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }
    
    /**
     * DELETE请求
     */
    async delete(url) {
        return this.request(url, { method: 'DELETE' });
    }
    
    /**
     * PUT请求
     */
    async put(url, data = {}) {
        return this.request(url, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }
}

// 创建HTTP客户端实例
const http = new HttpClient();

/**
 * API服务封装
 */
const api = {
    // ==================== 用户认证 ====================
    
    /**
     * 用户登录
     * @param {Object} credentials - { phone, password }
     */
    async login(credentials) {
        const result = await http.post(API_ENDPOINTS.auth.login, credentials);
        if (result.success && result.data) {
            http.setToken(result.data);
        }
        return result;
    },
    
    /**
     * 用户注册
     * @param {Object} userData - { name, password, phone, email }
     */
    async register(userData) {
        return await http.post(API_ENDPOINTS.auth.register, userData);
    },
    
    /**
     * 用户登出
     */
    async logout() {
        const result = await http.post(API_ENDPOINTS.auth.logout);
        http.removeToken();
        return result;
    },
    
    // ==================== 用户信息 ====================
    
    /**
     * 获取用户信息
     */
    async getUserInfo() {
        return await http.get(API_ENDPOINTS.user.getInfo);
    },
    
    /**
     * 更新用户信息
     * @param {Object} userData - { name, phone, email, age, gender }
     */
    async updateUserInfo(userData) {
        return await http.post(API_ENDPOINTS.user.updateInfo, userData);
    },
    
    /**
     * 更新密码
     * @param {string} password - 新密码
     */
    async updatePassword(password) {
        return await http.post(API_ENDPOINTS.user.updatePassword, password);
    },
    
    // ==================== 身体数据 ====================
    
    /**
     * 获取身体数据列表
     */
    async getBodyData() {
        return await http.post(API_ENDPOINTS.body.get);
    },
    
    /**
     * 添加身体数据
     * @param {Object} bodyData - { height, weight, fat_pre, sugar, pressure_h, pressure_l, date }
     */
    async addBodyData(bodyData) {
        return await http.post(API_ENDPOINTS.body.add, bodyData);
    },
    
    /**
     * 更新身体数据
     * @param {number} id - 数据ID
     * @param {Object} bodyData - { height, weight, fat_pre, sugar, pressure_h, pressure_l, date }
     */
    async updateBodyData(id, bodyData) {
        return await http.post(`${API_ENDPOINTS.body.update}/${id}`, bodyData);
    },
    
    /**
     * 删除身体数据
     * @param {number} id - 数据ID
     */
    async deleteBodyData(id) {
        return await http.delete(`${API_ENDPOINTS.body.delete}/${id}`);
    },
    
    // ==================== 饮食记录 ====================
    
    /**
     * 获取饮食记录
     */
    async getDietData() {
        return await http.get(API_ENDPOINTS.diet.get);
    },
    
    /**
     * 添加饮食记录
     */
    async addDietData(dietData) {
        return await http.post(API_ENDPOINTS.diet.add, dietData);
    },
    
    /**
     * 更新饮食记录
     */
    async updateDietData(id, dietData) {
        return await http.post(`${API_ENDPOINTS.diet.update}/${id}`, dietData);
    },
    
    /**
     * 删除饮食记录
     */
    async deleteDietData(id) {
        return await http.delete(`${API_ENDPOINTS.diet.delete}/${id}`);
    },
    
    // ==================== 睡眠管理 ====================
    
    /**
     * 获取睡眠数据
     * TODO: 后端接口未实现，实体类为空
     */
    async getSleepData() {
        // TODO: 后端接口未实现，使用模拟数据
        console.warn('睡眠管理接口未实现，使用模拟数据');
        return this.getMockSleepData();
    },
    
    /**
     * 添加睡眠数据
     * TODO: 后端接口未实现
     */
    async addSleepData(sleepData) {
        // TODO: 后端接口未实现
        console.warn('睡眠管理接口未实现');
        return { success: false, message: '后端接口未实现' };
    },
    
    // ==================== 健身管理 ====================
    
    /**
     * 获取健身数据
     * TODO: 后端接口未实现，实体类为空
     */
    async getFitnessData() {
        // TODO: 后端接口未实现，使用模拟数据
        console.warn('健身管理接口未实现，使用模拟数据');
        return this.getMockFitnessData();
    },
    
    /**
     * 添加健身数据
     * TODO: 后端接口未实现
     */
    async addFitnessData(fitnessData) {
        // TODO: 后端接口未实现
        console.warn('健身管理接口未实现');
        return { success: false, message: '后端接口未实现' };
    },
    
    // ==================== 社交广场 ====================
    
    /**
     * 获取帖子列表
     * @param {string} category - 分类筛选（可选）
     */
    async getPosts(category) {
        let url = API_ENDPOINTS.social.getPosts;
        if (category && category !== 'all') {
            url += '?category=' + encodeURIComponent(category);
        }
        return await http.get(url);
    },
    
    /**
     * 发布帖子
     * @param {Object} postData - { category, content, date }
     */
    async addPost(postData) {
        return await http.post(API_ENDPOINTS.social.addPost, postData);
    },
    
    /**
     * 点赞
     * @param {number} id - 帖子ID
     */
    async likePost(id) {
        return await http.post(`${API_ENDPOINTS.social.likePost}/${id}`);
    },
    
    /**
     * 评论
     * @param {number} id - 帖子ID
     */
    async commentPost(id) {
        return await http.post(`${API_ENDPOINTS.social.commentPost}/${id}`);
    },
    
    /**
     * 删除帖子
     * @param {number} id - 帖子ID
     */
    async deletePost(id) {
        return await http.delete(`${API_ENDPOINTS.social.deletePost}/${id}`);
    },
    
    // ==================== 模拟数据 ====================
    
    /**
     * 获取模拟睡眠数据
     */
    getMockSleepData() {
        return {
            success: true,
            data: [
                { id: 1, date: '2024-04-15', startTime: '22:30', endTime: '06:30', duration: 8, quality: 85, deepSleep: 3.5, remark: '睡眠质量良好' },
                { id: 2, date: '2024-04-14', startTime: '23:00', endTime: '07:00', duration: 8, quality: 78, deepSleep: 3.0, remark: '中途醒来一次' },
                { id: 3, date: '2024-04-13', startTime: '00:00', endTime: '08:00', duration: 8, quality: 65, deepSleep: 2.5, remark: '入睡较晚' },
                { id: 4, date: '2024-04-12', startTime: '22:00', endTime: '06:00', duration: 8, quality: 90, deepSleep: 4.0, remark: '睡眠质量优秀' },
                { id: 5, date: '2024-04-11', startTime: '23:30', endTime: '06:30', duration: 7, quality: 70, deepSleep: 2.8, remark: '睡眠时间不足' }
            ]
        };
    },
    
    /**
     * 获取模拟健身数据
     */
    getMockFitnessData() {
        return {
            success: true,
            data: [
                { id: 1, date: '2024-04-15', type: '跑步', duration: 45, calories: 350, distance: 5.2, heartRate: 145, remark: '晨跑' },
                { id: 2, date: '2024-04-14', type: '力量训练', duration: 60, calories: 280, sets: 20, remark: '上肢训练' },
                { id: 3, date: '2024-04-13', type: '游泳', duration: 50, calories: 400, distance: 1.5, remark: '自由泳' },
                { id: 4, date: '2024-04-12', type: '瑜伽', duration: 40, calories: 150, remark: '放松训练' },
                { id: 5, date: '2024-04-11', type: '骑行', duration: 90, calories: 500, distance: 25, remark: '户外骑行' }
            ]
        };
    }
};

// 导出API
window.api = api;
window.http = http;
