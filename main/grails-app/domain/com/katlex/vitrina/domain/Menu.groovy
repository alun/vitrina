package com.katlex.vitrina.domain

class Menu {
	
    String path
    Integer sequencer
    String type
    String command
    String parameters
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
    Long parent = 0
	
	static hasMany = [
    	permissions : String
    ]

    static mapping = {
        columns {
            path column: "menu_path", index: "menu_path_idx"
            parent index: "menu_parent_idx"
        }
		permissions lazy: false
    }
    
    static constraints = {
		parameters nullable: true
        path(blank:false, size: 1..100, unique: true, validator: {val, obj ->
                if (val.startsWith(".") || val.endsWith(".") || val.contains("..")) {
                    return false
                }

                char c
                int cp
                for (int i = 0; i < val.length(); i++) {
                    c = val.charAt(i)
                    if (c != '.') {
                        if (!Character.isLetterOrDigit(val.codePointAt(i))) {
                            return false
                        }

                        if (Character.isHighSurrogate(c)) i++
                    }

                }

                if (val.contains(".")) {
                    def opt = Menu.findByPathAndType(val.substring(0, val.lastIndexOf(".")), MenuItemType.SUBMENU)
                    if (!opt) return false

                    obj.parent = opt.id
                } else {
                    obj.parent = 0
                }

                return true
            })
        type(blank: false, inList: [MenuItemType.ACTION, MenuItemType.SUBMENU, MenuItemType.URL])
        command(nullable: true, size: 1..100, validator: {val, obj ->
                if (obj.type == MenuItemType.SUBMENU) {
                    if (val) return false
                } else if (!val) {
                    return false
                } else if (obj.type == MenuItemType.ACTION) {
                    if (val.contains(" ") || val.startsWith(".") || val.endsWith(".") || !val.contains(".") || val.indexOf(".") != val.lastIndexOf(".")) {
                        return false
                    }
                } else if (obj.type == MenuItemType.URL) {
                    try {
                        new URL(val)
                    } catch (MalformedURLException mue) {
                        return false
                    }
                }

                return true
            })
       
    }

    
    @Override
	public boolean equals(Object obj) {
		return ( obj instanceof Menu ) && obj.id == this.id;
	}


	public String toString() {
        return path
    }
}
