-- ----------------------------
-- Table structure for good_types
-- ----------------------------
DROP TABLE IF EXISTS `good_types`;
CREATE TABLE `good_types` (
  `tgt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tgt_name` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '类型名称',
  `tgt_is_show` char(1) DEFAULT NULL COMMENT '是否显示',
  `tgt_order` int(2) DEFAULT NULL COMMENT '类型排序',
  PRIMARY KEY (`tgt_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of good_types
-- ----------------------------
INSERT INTO `good_types` VALUES ('1', '绿色蔬菜', '1', '1');
INSERT INTO `good_types` VALUES ('2', '根茎类', '1', '2');
INSERT INTO `good_types` VALUES ('3', '菌类', '1', '3');

-- ----------------------------
-- Table structure for good_infos
-- ----------------------------
DROP TABLE IF EXISTS `good_infos`;
CREATE TABLE `good_infos` (
  `tg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tg_title` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '商品标题',
  `tg_price` decimal(8,2) DEFAULT NULL COMMENT '商品单价',
  `tg_unit` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '单位',
  `tg_order` varchar(255) DEFAULT NULL COMMENT '排序',
  `tg_type_id` int(11) DEFAULT NULL COMMENT '类型外键编号',
  PRIMARY KEY (`tg_id`),
  KEY `tg_type_id` (`tg_type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of good_infos
-- ----------------------------
INSERT INTO `good_infos` VALUES ('1', '金针菇', '5.50', '斤', '1', '3');
INSERT INTO `good_infos` VALUES ('2', '油菜', '12.60', '斤', '2', '1');