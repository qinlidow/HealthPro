/**
 * API配置和请求封装
 * 基于后端已实现的接口
 */

// API基础配置
const API_BASE_URL = 'http://localhost:8080';

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
        get: '/Sleep/GetSleep',
        getById: '/Sleep/GetSleepById',
        add: '/Sleep/AddSleep',
        update: '/Sleep/SetSleep',
        delete: '/Sleep/DeleteSleep'
    },
    
    // 健身管理相关
    fitness: {
        get: '/Fitness/GetFitness',
        add: '/Fitness/AddFitness',
        update: '/Fitness/SetFitness',
        delete: '/Fitness/DeleteFitness'
    },
    
    // 社交广场相关（已实现）
    social: {
        getPosts: '/Social/GetPosts',               // GET - 获取帖子列表
        getFollowingPosts: '/Social/GetFollowingPosts', // GET - 获取关注的人的帖子
        getPostById: '/Social/GetPostById',          // GET - 获取帖子详情
        getPostsByUser: '/Social/GetPostsByUser',    // GET - 获取用户的帖子
        addPost: '/Social/AddPost',                  // POST - 发布帖子
        likePost: '/Social/LikePost',                // POST - 点赞
        deletePost: '/Social/DeletePost',            // DELETE - 删除帖子
        getPendingPosts: '/Social/GetPendingPosts',  // GET - 获取待审核帖子
        approvePost: '/Social/ApprovePost',          // POST - 审核通过
        rejectPost: '/Social/RejectPost',            // POST - 审核拒绝
        toggleTop: '/Social/ToggleTop',              // POST - 切换置顶
        getComments: '/Social/GetComments',          // GET - 获取评论列表
        addComment: '/Social/AddComment',            // POST - 添加评论
        deleteComment: '/Social/DeleteComment',      // DELETE - 删除评论
        follow: '/Social/Follow',                    // POST - 关注
        unfollow: '/Social/Unfollow',                // POST - 取消关注
        isFollowing: '/Social/IsFollowing',          // GET - 是否已关注
        getFollowingList: '/Social/GetFollowingList', // GET - 关注列表
        getFollowerList: '/Social/GetFollowerList',  // GET - 粉丝列表
        getFollowerCount: '/Social/GetFollowerCount', // GET - 粉丝数
        getFollowingCount: '/Social/GetFollowingCount', // GET - 关注数
        favorite: '/Social/Favorite',                   // POST - 收藏帖子
        unfavorite: '/Social/Unfavorite',               // POST - 取消收藏
        isFavorite: '/Social/IsFavorite',               // GET - 是否已收藏
        getFavoritePosts: '/Social/GetFavoritePosts',   // GET - 获取收藏的帖子列表
        getFavoriteCount: '/Social/GetFavoriteCount',   // GET - 获取收藏数
        unlikePost: '/Social/UnlikePost',               // POST - 取消点赞
        isLiked: '/Social/IsLiked'                      // GET - 是否已点赞
    },

    // 打卡相关
    checkIn: {
        doCheckIn: '/CheckIn/Do',           // POST - 打卡
        isChecked: '/CheckIn/IsChecked',    // GET - 是否已打卡
        list: '/CheckIn/List',              // GET - 打卡列表
        byMonth: '/CheckIn/ByMonth',        // GET - 按月获取打卡记录
        count: '/CheckIn/Count',            // GET - 打卡总数
        streak: '/CheckIn/Streak'           // GET - 连续打卡天数
    },

    // 文件上传相关
    file: {
        upload: '/File/Upload'                       // POST - 上传文件
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

    async getSleepData() {
        return await http.get(API_ENDPOINTS.sleep.get);
    },

    async getSleepById(id) {
        return await http.get(`${API_ENDPOINTS.sleep.getById}/${id}`);
    },

    async addSleepData(sleepData) {
        return await http.post(API_ENDPOINTS.sleep.add, sleepData);
    },

    async updateSleepData(id, sleepData) {
        return await http.post(`${API_ENDPOINTS.sleep.update}/${id}`, sleepData);
    },

    async deleteSleepData(id) {
        return await http.delete(`${API_ENDPOINTS.sleep.delete}/${id}`);
    },
    
    // ==================== 健身管理 ====================

    async getFitnessData() {
        return await http.get(API_ENDPOINTS.fitness.get);
    },

    async addFitnessData(fitnessData) {
        return await http.post(API_ENDPOINTS.fitness.add, fitnessData);
    },

    async updateFitnessData(id, fitnessData) {
        return await http.post(`${API_ENDPOINTS.fitness.update}/${id}`, fitnessData);
    },

    async deleteFitnessData(id) {
        return await http.delete(`${API_ENDPOINTS.fitness.delete}/${id}`);
    },
    
    // ==================== 社交广场 ====================
    
    async getPosts(category) {
        let url = API_ENDPOINTS.social.getPosts;
        if (category && category !== 'all') {
            url += '?category=' + encodeURIComponent(category);
        }
        return await http.get(url);
    },

    async getFollowingPosts() {
        return await http.get(API_ENDPOINTS.social.getFollowingPosts);
    },

    async getPostById(id) {
        return await http.get(`${API_ENDPOINTS.social.getPostById}/${id}`);
    },

    async getPostsByUser(userId) {
        return await http.get(`${API_ENDPOINTS.social.getPostsByUser}/${userId}`);
    },

    async addPost(postData) {
        return await http.post(API_ENDPOINTS.social.addPost, postData);
    },

    async likePost(id) {
        return await http.post(`${API_ENDPOINTS.social.likePost}/${id}`);
    },

    async unlikePost(id) {
        return await http.post(`${API_ENDPOINTS.social.unlikePost}/${id}`);
    },

    async isLiked(id) {
        return await http.get(`${API_ENDPOINTS.social.isLiked}/${id}`);
    },

    async deletePost(id) {
        return await http.delete(`${API_ENDPOINTS.social.deletePost}/${id}`);
    },

    async getPendingPosts() {
        return await http.get(API_ENDPOINTS.social.getPendingPosts);
    },

    async approvePost(id) {
        return await http.post(`${API_ENDPOINTS.social.approvePost}/${id}`);
    },

    async rejectPost(id) {
        return await http.post(`${API_ENDPOINTS.social.rejectPost}/${id}`);
    },

    async toggleTop(id) {
        return await http.post(`${API_ENDPOINTS.social.toggleTop}/${id}`);
    },

    async getComments(postId) {
        return await http.get(`${API_ENDPOINTS.social.getComments}/${postId}`);
    },

    async addComment(commentData) {
        return await http.post(API_ENDPOINTS.social.addComment, commentData);
    },

    async deleteComment(id) {
        return await http.delete(`${API_ENDPOINTS.social.deleteComment}/${id}`);
    },

    async followUser(followingId) {
        return await http.post(`${API_ENDPOINTS.social.follow}/${followingId}`);
    },

    async unfollowUser(followingId) {
        return await http.post(`${API_ENDPOINTS.social.unfollow}/${followingId}`);
    },

    async isFollowing(followingId) {
        return await http.get(`${API_ENDPOINTS.social.isFollowing}/${followingId}`);
    },

    async getFollowingList(userId) {
        return await http.get(`${API_ENDPOINTS.social.getFollowingList}/${userId}`);
    },

    async getFollowerList(userId) {
        return await http.get(`${API_ENDPOINTS.social.getFollowerList}/${userId}`);
    },

    async getFollowerCount(userId) {
        return await http.get(`${API_ENDPOINTS.social.getFollowerCount}/${userId}`);
    },

    async getFollowingCount(userId) {
        return await http.get(`${API_ENDPOINTS.social.getFollowingCount}/${userId}`);
    },

    async favoritePost(postId) {
        return await http.post(`${API_ENDPOINTS.social.favorite}/${postId}`);
    },

    async unfavoritePost(postId) {
        return await http.post(`${API_ENDPOINTS.social.unfavorite}/${postId}`);
    },

    async isFavorite(postId) {
        return await http.get(`${API_ENDPOINTS.social.isFavorite}/${postId}`);
    },

    async getFavoritePosts() {
        return await http.get(API_ENDPOINTS.social.getFavoritePosts);
    },

    async getFavoriteCount() {
        return await http.get(API_ENDPOINTS.social.getFavoriteCount);
    },

    // ==================== 打卡 ====================

    async doCheckIn(date) {
        return await http.post(API_ENDPOINTS.checkIn.doCheckIn, { date });
    },

    async isCheckedIn(date) {
        return await http.get(`${API_ENDPOINTS.checkIn.isChecked}?date=${encodeURIComponent(date)}`);
    },

    async getCheckInList() {
        return await http.get(API_ENDPOINTS.checkIn.list);
    },

    async getCheckInByMonth(yearMonth) {
        return await http.get(`${API_ENDPOINTS.checkIn.byMonth}?yearMonth=${encodeURIComponent(yearMonth)}`);
    },

    async getCheckInCount() {
        return await http.get(API_ENDPOINTS.checkIn.count);
    },

    async getCheckInStreak() {
        return await http.get(API_ENDPOINTS.checkIn.streak);
    },

    // ==================== 文件上传 ====================

    async uploadFile(file) {
        const formData = new FormData();
        formData.append('file', file);
        const token = http.getToken();
        try {
            const response = await fetch(API_BASE_URL + API_ENDPOINTS.file.upload, {
                method: 'POST',
                headers: token ? { 'Authorization': `Bearer ${token}` } : {},
                body: formData
            });
            const data = await response.json();
            if (data.code === 200) {
                return { success: true, data: data.data, message: data.message };
            } else {
                return { success: false, data: null, message: data.message };
            }
        } catch (error) {
            console.error('文件上传错误:', error);
            return { success: false, data: null, message: '文件上传失败' };
        }
    }
};

// 导出API
window.api = api;
window.http = http;
