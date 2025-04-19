create database if not exists home_school_platform;
use home_school_platform;

drop table if exists admin_user;
drop table if exists user_role;
drop table if exists admin_role;
drop table if exists role_menu;
drop table if exists admin_menu;

drop table if exists parent_info;
drop table if exists student_info;
drop table if exists academy_info;
drop table if exists major_info;
drop table if exists memorabilia_info;

drop table if exists star_info;
drop table if exists activity_info;
drop table if exists parent_advice;
drop table if exists course_info;
drop table if exists student_rank;

drop table if exists major_data;
drop table if exists major_course;


/**
  1.管理员信息表
 */
create table admin_user (
    admin_id      bigint(20)      not null auto_increment    comment '管理员id',
    username      varchar(20)     not null unique            comment '用户名',
    password      varchar(100)    not null                   comment '密码',
    admin_name    varchar(20)     not null                   comment '管理员name',
    academy_id    bigint(20)      not null                   comment '书院id',
    primary key (admin_id)
) engine=innodb comment = '管理员信息表';

insert into admin_user values
(1,  'dagong',   'Beewa8j88e5bU485cf+ddOb5+8f743i5c7b1Kd6B2fU01X0a', '大工书院负责人',       1),
(2,  'dayu',     'G89cf8EfcWf7S9ej2c392Df2Ab9i74Odajbc44bj4f5f77d0', '大煜书院负责人',       2),
(3,  'bochuan',  '430f/b9bNfP9Vafdy7DfP2ee/5z2c6Fd0ed0648b6958f5a7', '伯川书院负责人',       3),
(4,  'lingxi',   '02d775774z55964tc6H22effq73s16n26X1a53842ak6fic1', '令希书院负责人',       4),
(5,  'houde',    'Ee5bJ1F1wfib86Hdk2C4v2M3P0d1eee67452dd28fc79f0a4', '厚德书院负责人',       5),
(6,  'zhixing',  'i288J5e68aS289XdfbAcr09dB6r7W6Uea94e660fa1a8d06d', '知行书院负责人',       6),
(7,  'duxue',    'm6T6N1+b77R3l1cdD8R3caebm9a5T5o294b27cd9ca9c91df', '笃学书院负责人',       7),
(8,  'qiushi',   'b1aCbbB97D7366aH03x28n4dGd043a+d6kf3KacQ44t1fK40', '求实书院负责人',       8),
(9,  'ziqiang',  '7bWcEbe5g3adV4Y1g6Y010D342o6f4i36e6f1e487a23f73f', '自强书院负责人',       9),
(10, 'weilai',   'z18Ya4aea507Zc01b28884aeWafF97pa0m4cI98gd5Iaa981', '未来书院负责人',       10),
(11, 'zhongxin', 'L95h9cR9dR6fc21Rbf105m98Idcaf0/8aL07r34n5abe4q15', '中心食堂负责人',       1),
(12, 'diliu',    '/0bu7cWefz26if7T092f5Zeb93eG360c7j00e6db441ec2a3', '第六学生食堂负责人',    8),
(13, 'fudao',    'Y1bfc74d7q89N4bJb7/00y3fo5dReeNd095fP46Od8A0fp19', '求实书院辅导员',       8),
(14, 'wenti',    'P93E27O92Nd9E2dKb7Ta4l70X5am80Ka6Nc0Ua96a0U0aF49', '求实书院文体板块负责人', 8),
(15, 'jiaoxue',  'P38Ef0zd5h67Bb2F45S62q8304d49df4d6f263aE00s83Zba', '求实书院教学板块负责人', 8);


/**
  2.用户和角色关联表
 */
create table user_role (
    admin_id    bigint(20)    not null    comment '管理员id',
    role_id     bigint(20)    not null    comment '角色id',
    primary key (admin_id, role_id)
) engine=innodb comment = '用户和角色关联表';

insert into user_role values
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2),
(11, 5),
(12, 5),
(13, 6),
(14, 3),
(15, 4);


/**
  3.管理员角色表
 */
create table admin_role (
    role_id      bigint(20)     not null auto_increment    comment '角色id',
    role_name    varchar(20)    not null                   comment '角色名',
    primary key (role_id)
) engine=innodb comment = '管理员角色表';

insert into admin_role values
(1, '大工书院负责人'),
(2, '子书院负责人'),
(3, '文体板块负责人'),
(4, '教学板块负责人'),
(5, '职能部门负责人'),
(6, '辅导员');


/**
  4.角色和菜单关联表
 */
create table role_menu (
    role_id    bigint(20)    not null    comment '角色id',
    menu_id    bigint(20)    not null    comment '菜单id',
    primary key (role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

insert into role_menu values
(1, 1),
(1, 10),
(1, 11),
(1, 100),
(1, 101),
(1, 102),
(1, 103),
(1, 110),
(1, 111),
(1, 112),
(1, 113),
(1, 2),
(1, 200),
(1, 4),
(1, 400),
(1, 401),

(2, 1),
(2, 10),
(2, 11),
(2, 100),
(2, 101),
(2, 102),
(2, 103),
(2, 110),
(2, 111),
(2, 112),
(2, 113),
(2, 2),
(2, 200),
(2, 4),
(2, 400),
(2, 401),

(3, 1),
(3, 12),
(3, 120),
(3, 121),
(3, 122),
(3, 123),

(4, 1),
(4, 13),
(4, 130),
(4, 131),
(4, 132),
(4, 133),

(5, 2),
(5, 200),

(6, 2),
(6, 200),
(6, 3),
(6, 30),
(6, 31),
(6, 300),
(6, 5);


/**
  5.后台系统菜单表
 */
create table admin_menu (
    menu_id       bigint(20)      not null auto_increment    comment '菜单id',
    menu_name     varchar(20)     not null                   comment '菜单名称',
    parent_id     bigint(20)      default 0                  comment '父菜单id',
    path          varchar(100)    default ''                 comment '路由地址',
    component     varchar(100)    default null               comment '组件地址',
    menu_type     char(1)         not null                   comment '菜单类型 父目录(P) 子目录(C) 按钮(B)',
    permission    varchar(100)    default null               comment '权限标识',
    icon          varchar(50)     default '#'                comment '菜单图标',
    primary key (menu_id)
) engine=innodb comment = '后台系统菜单表';

insert into admin_menu values
(1, '内容管理', 0, 'content', null,            'P', null,          'iconfont icon-content'),
(2, '建议管理', 0, 'advice',  'advice/Advice', 'P', 'advice:list', 'iconfont icon-advice'),
(3, '学生管理', 0, 'student', null,            'P', null,          'iconfont icon-ic_student'),
(4, '成员管理', 0, 'user',    'user/User',     'P', 'user:list',   'iconfont icon-member'),
(5, '专业数据', 0, 'data',    'data/Data',     'P', 'data:list',   'iconfont icon-data'),
-- ===========================================================================================================
(10, '事记管理',    1, 'memorabilia', 'content/memorabilia/Memorabilia', 'C', 'content:memorabilia:list', 'iconfont icon-memorabilia'),
(11, '书院之星管理', 1, 'star',        'content/star/Star',               'C', 'content:star:list',        'iconfont icon-Star'),
(12, '文体活动管理', 1, 'activity',    'content/activity/Activity',       'C', 'content:activity:list',    'iconfont icon-activity'),
(13, '学习天地管理', 1, 'course',      'content/course/Course',           'C', 'content:course:list',      'iconfont icon-fs-course'),
-- -----------------------------------------------------------------------------------------------------------
(30, '信息管理', 3, 'studentInfo', 'student/studentInfo/StudentInfo', 'C', 'student:studentInfo:list', 'iconfont icon-a-Studentinformation'),
(31, '排名管理', 3, 'rank',        'student/rank/Rank',               'C', 'student:rank:list',        'iconfont icon-rank'),
-- ===========================================================================================================
(100, '事记新增', 10, '', '', 'B', 'content:memorabilia:add',   '#'),
(101, '事记删除', 10, '', '', 'B', 'content:memorabilia:del',   '#'),
(102, '事记修改', 10, '', '', 'B', 'content:memorabilia:edit',  '#'),
(103, '事记查询', 10, '', '', 'B', 'content:memorabilia:query', '#'),
(110, '书院之星新增', 11, '', '', 'B', 'content:star:add',   '#'),
(111, '书院之星删除', 11, '', '', 'B', 'content:star:del',   '#'),
(112, '书院之星修改', 11, '', '', 'B', 'content:star:edit',  '#'),
(113, '书院之星查询', 11, '', '', 'B', 'content:star:query', '#'),
(120, '文体活动新增', 12, '', '', 'B', 'content:activity:add',   '#'),
(121, '文体活动删除', 12, '', '', 'B', 'content:activity:del',   '#'),
(122, '文体活动修改', 12, '', '', 'B', 'content:activity:edit',  '#'),
(123, '文体活动查询', 12, '', '', 'B', 'content:activity:query', '#'),
(130, '学习天地新增', 13, '', '', 'B', 'content:course:add',   '#'),
(131, '学习天地删除', 13, '', '', 'B', 'content:course:del',   '#'),
(132, '学习天地修改', 13, '', '', 'B', 'content:course:edit',  '#'),
(133, '学习天地查询', 13, '', '', 'B', 'content:course:query', '#'),
-- -----------------------------------------------------------------------------------------------------------
(200, '建议回复', 2, '', '', 'B', 'advice:reply', '#'),
-- -----------------------------------------------------------------------------------------------------------
(300, '成绩导入', 30, '', '', 'B', 'student:grade:reply', '#'),
-- -----------------------------------------------------------------------------------------------------------
(400, '成员注册', 40, '', '', 'B', 'user:register', '#'),
(401, '成员注销', 40, '', '', 'B', 'user:del',      '#');


/**
  6.家长信息表
 */
create table parent_info (
    parent_id         bigint(20)      not null auto_increment    comment '家长id',
    student_number    bigint(20)      not null                   comment '孩子学号',
    password          varchar(100)    not null                   comment '密码',
    phone_number      varchar(20)     default null               comment '手机号码',
    primary key (parent_id)
) engine=innodb comment = '家长信息表';


/**
  7.学生信息表
 */
create table student_info (
    student_number    bigint(20)      not null        comment '学号',
    student_name      varchar(20)     not null        comment '学生姓名',
    academy_name      varchar(20)     not null        comment '书院名称',
    major_id          bigint(20)      not null        comment '专业id',
    student_class     varchar(10)     not null        comment '学生班级',
    primary key (student_number)
) engine=innodb comment = '学生信息表';


/**
  8.书院信息表
 */
create table academy_info (
    academy_id      bigint(20)     not null    comment '书院id',
    academy_name    varchar(20)    not null    comment '书院名称',
    primary key (academy_id)
) engine=innodb comment = '书院信息表';

insert into academy_info values
(1, '大工书院'),
(2, '大煜书院'),
(3, '伯川书院'),
(4, '令希书院'),
(5, '厚德书院'),
(6, '知行书院'),
(7, '笃学书院'),
(8, '求实书院'),
(9, '自强书院'),
(10, '未来书院');


/**
  9.书院信息表
 */
create table major_info (
    major_id          bigint(20)     not null      comment '专业id',
    academy_name      varchar(20)    not null      comment '书院名称',
    major_category    varchar(20)    not null      comment '专业类别',
    major_name        varchar(20)    default ''    comment '专业名称',
    is_divert         char(1)        default '0'   comment '是否分流 0->未分流 1->已分流',
    primary key (major_id)
) engine=innodb comment = '专业信息表';

insert into major_info values
(1, '求实书院', '软件工程', '', '0'),
(2, '求实书院', '软件工程', '软件工程', '1'),
(3, '求实书院', '软件工程', '网络工程', '1'),
(4, '求实书院', '电子信息与技术', '', '0'),
(5, '求实书院', '电子信息与技术', '集成电路设计与集成系统', '1'),
(6, '求实书院', '电子信息与技术', '电子科学与技术', '1');


/**
  10.大事记信息表
 */
create table memorabilia_info (
    memorabilia_id         bigint(20)      not null auto_increment    comment '大事记id',
    memorabilia_time       date            not null                   comment '大事记时间',
    memorabilia_content    varchar(200)    default ''                 comment '大事记内容',
    academy_name           varchar(20)     not null                   comment '大事记所属书院',
    primary key (memorabilia_id)
) engine=innodb comment = '大事记信息表';


/**
  11.书院之星信息表
 */
create table star_info (
    star_id           bigint(20)      not null auto_increment    comment '书院之星id',
    student_number    bigint(20)      not null                   comment '书院之星学号',
    star_content      longtext        not null                   comment '书院之星内容',
    static_url        varchar(150)    not null                   comment '内容地址',
    primary key (star_id)
) engine=innodb comment = '书院之星信息表';


/**
  12.文体活动信息表
 */
create table activity_info (
    activity_id          bigint(20)      not null auto_increment    comment '文体活动id',
    activity_time        varchar(50)     not null                   comment '文体活动时间',
    activity_location    varchar(100)    not null                   comment '文体活动地点',
    activity_content     longtext        not null                   comment '文体活动内容',
    primary key (activity_id)
) engine=innodb comment = '文体活动信息表';


/**
  13.家长建议表
 */
create table parent_advice (
    advice_id         bigint(20)    not null auto_increment    comment '建议id',
    parent_id         bigint(20)    not null                   comment '家长id',
    advice_content    longtext      not null                   comment '建议内容',
    admin_id          bigint(20)    not null                   comment '建议对象',
    reply             longtext      not null                   comment '回复',
    status            char(1)       default '0'                comment '建议状态 0->待处理 1->已处理 2->不合法',
    primary key (advice_id)
) engine=innodb comment = '家长建议表';


/**
  14.课程信息表
 */
create table course_info (
    course_id             bigint(20)     not null auto_increment    comment '课程id',
    course_name           varchar(20)    not null                   comment '课程名称',
    course_description    longtext       not null                   comment '课程描述',
    is_compulsory         char(1)        not null                   comment '是否必修',
    is_postgraduate       char(1)        not null                   comment '是否保研参算',
    primary key (course_id)
) engine=innodb comment = '课程信息表';


/**
  15.学生排名表
 */
create table student_rank (
    student_number   bigint(20)    not null auto_increment    comment '学号',
    _1               int(5)        default null               comment '大一上排名',
    _2               int(5)        default null               comment '大一下排名',
    _3               int(5)        default null               comment '大二上排名',
    _4               int(5)        default null               comment '大二下排名',
    _5               int(5)        default null               comment '大三上排名',
    _6               int(5)        default null               comment '大三下排名',
    primary key (student_number)
) engine=innodb comment = '学生排名表';


/**
  16.专业数据表
 */
create table major_data (
    data_id           bigint(20)    not null auto_increment    comment '数据id',
    major_id          bigint(20)    not null                   comment '专业id',
    total_students    int(5)        not null                   comment '专业人数',
    graduate_rate     int(5)        not null                   comment '保研率',
    year              int(5)        not null                   comment '20xx级',
    primary key (data_id)
) engine=innodb comment = '专业数据表';


/**
  17.专业课程表
 */
create table major_course (
    major_id     bigint(20)    not null    comment '专业id',
    course_id    bigint(20)    not null    comment '课程id',
    primary key (major_id, course_id)
) engine=innodb comment = '专业课程表';
