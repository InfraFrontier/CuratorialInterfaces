// gracefully degrading firebug console methods
if (! ("console" in window) || !("firebug" in console)) {
    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
    window.console = {};
    for (var i = 0; i < names.length; ++i) window.console[names[i]] = function() {};
}

// init
$(function() {
    // clear form
    $('#submission').clearForm();

    // focus on next button at splash step
    $('#next').focus();

    // initialize storage, session and user id
    $.storage = new $.store();
    $.storage.driver.scope == "browser";
    var uid;
    var current_date = $.easydate.get_now();
    var current_session = {
        date_started: current_date,
        complete: false
    };

    // setup help tips
    $('input, textarea, select').tipTip({
        activation: 'focus',
        maxWidth: "40%",
        defaultPosition: 'top',
    });

    // get mutation templates from html
    var $mutation_template = $("#genotype > fieldset");
    layout_mutation($mutation_template);
    $mutation_template = $mutation_template.clone();

    // get reference templates from html
    var $reference_template = $("#references > fieldset");
    $reference_template = $reference_template.clone();

    function layout_mutation($mutation) {
        var $mutation_type_select = $('.mutation_type select', $mutation);
        var mutation_type_selection = $mutation_type_select.val();

        var $mutation_subtype_select = $('.mutation_subtype select', $mutation);
        $mutation_subtype_select.val("");

        $('.conditional', $mutation).hide();

        // set child selection
        if (mutation_type_selection) {
            $('.' + mutation_type_selection, $mutation).show();
        }

        // toggle mutation original genetic background
        toggle_mutation_original_backg_other($mutation);
    }

    // add/remove mutations and references using templates
    function add_mutation(index, fadein) {
        if ($("#genotype > fieldset").length < 10) {
            var $add = $('#add_mutation').parent();
            var $mutation = build_mutation(index);
            layout_mutation($mutation);
            if (fadein) {
                $mutation.hide().fadeIn(500);
            }
            $add.before($mutation);
            if (fadein) {
                $('select', $mutation).first().focus();
            }
        } else {
            alert('You cannot add more than ten mutations');
        }
    }

    $("#add_mutation").click(function() {
        add_mutation(null, true);
        return false;
    });

    function build_mutation(index) {
        if (!index) {
            index = 0;
            $("#genotype > fieldset").each(function(i, el) {
                var name = $('select[name^=mutation_type_]', $(el)).attr('name');
                var new_index = parseInt(name.substring(name.length - 1));
                if (index < new_index) {
                    index = new_index
                }
                index++;
            });
        }
        var $mutation = $mutation_template.clone();
        $('input[type!=button], select', $mutation).each(function(i, el) {
            $(this).val('');
            var name = $(this).attr('name');
            var new_name = name.substring(0, name.length - 1) + index;
            $(this).attr('name', new_name);
            $(this).attr('id', new_name);
            // TODO: change name of label as well!
        });
        return $mutation;
    }

    function add_reference(index, fadein) {
        if ($("#references > fieldset").length < 10) {
            var $add = $('#add_reference').parent();
            var $reference = build_reference(index);
            if (fadein) {
                $reference.hide().fadeIn(500);
            }
            $add.before($reference);
            if (fadein) {
                $('input', $reference).first().focus();
            }
        } else {
            alert('You cannot add more than ten references');
        }
    }

    $("#add_reference").click(function() {
        add_reference(null, true);
        return false;
    });

    function build_reference(index) {
        if (!index) {
            index = 0;
            $("#references > fieldset").each(function(i, el) {
                var name = $('input[name^=reference_descr_]', $(el)).attr('name');
                var new_index = parseInt(name.substring(name.length - 1));
                if (index < new_index) {
                    index = new_index
                }
                index++;
            });
        }
        var $reference = $reference_template.clone();
        $('input[type!=button], select', $reference).each(function(i, el) {
            $(this).val('');
            var name = $(this).attr('name');
            var new_name = name.substring(0, name.length - 1) + index;
            $(this).attr('name', new_name);
            $(this).attr('id', new_name);
            // TODO: change name of label as well!
        });
        return $reference;
    }

    $(".remove_mutation").live('click',
    function() {
        var $mutation = $(this).parent().parent();
        $mutation.remove();
        $("#add_mutation").focus();
        return false;
    });

    $(".remove_reference").live('click',
    function() {
        var $reference = $(this).parent().parent();
        $reference.remove();
        $("#add_reference").focus();
        return false;
    });

    $(".mutation_type select").live('change keyup',
    function() {
        var $mutation = $(this).parent().parent().parent();
        layout_mutation($mutation);
    });


    // setup homozygous_matings_required_text
    function toggle_homozygous_matings_required_text() {
        if ($("input[name=homozygous_matings_required]:checked").val() == 'yes') {
            $("#homozygous_matings_required_text").show();
        } else {
            $("#homozygous_matings_required_text").val('');
            $("#homozygous_matings_required_text").hide();
        }
    };

    $("input[name=homozygous_matings_required]").bind('keyup change',
    function() {
        toggle_homozygous_matings_required_text();

    });



    // setup deposited_elsewhere_text
    function toggle_deposited_elsewhere_text() {
        if ($("input[name=deposited_elsewhere]:checked").val() == 'yes') {
            $("#deposited_elsewhere_text").show();
        } else {
            $("#deposited_elsewhere_text").val('');
            $("#deposited_elsewhere_text").hide();
        }
    };

    $("input[name=deposited_elsewhere]").bind('keyup change',
    function() {
        toggle_deposited_elsewhere_text();

    });


    // setup ip_rights_text
    function toggle_ip_rights_text() {
        if ($("input[name=ip_rights]:checked").val() == 'yes') {
            $("#ip_rights_text").show();
        } else {
            $("#ip_rights_text").val('');
            $("#ip_rights_text").hide();
        }
    };

    $("input[name=ip_rights]").bind('keyup change',
    function() {
        toggle_ip_rights_text();

    });


    // setup exclusive_owner_text
    function toggle_exclusive_owner_text() {
        if ($("input[name=exclusive_owner]:checked").val() == 'no') {
            $("#exclusive_owner_text").show();
        } else {
            $("#exclusive_owner_text").val('');
            $("#exclusive_owner_text").hide();
        }
    };

    $("input[name=exclusive_owner]").bind('keyup change',
    function() {
        toggle_exclusive_owner_text();

    });

    // setup delayed_release_text
    function toggle_delayed_release_text() {
        if ($("input[name=delayed_release]:checked").val() == 'yes') {
            $("#delayed_release_text").show();
        } else {
            $("#delayed_release_text").val('');
            $("#delayed_release_text").hide();
        }
    };

    $("input[name=delayed_release]").bind('keyup change',
    function() {
        toggle_delayed_release_text();

    });

    // setup research_areas_other_text
    function toggle_research_areas_other_text() {
        if ($("#research_areas_other").is(':checked')) {
            $("#research_areas_other_text").show();
        } else {
            $("#research_areas_other_text").val('');
            $("#research_areas_other_text").hide();
        }
    };

    $("#research_areas_other").bind('keyup change',
    function() {
        toggle_research_areas_other_text();

    });


    // setup research_tools_cre_text
    function toggle_research_tools_cre_text() {
        if ($("#research_tools_cre").is(':checked')) {
            $("#research_tools_cre_text").show();
        } else {
            $("#research_tools_cre_text").val('');
            $("#research_tools_cre_text").hide();
        }
    };

    $("#research_tools_cre").bind('keyup change',
    function() {
        toggle_research_tools_cre_text();

    });

    // setup research_tools_flp_text
    function toggle_research_tools_flp_text() {
        if ($("#research_tools_flp").is(':checked')) {
            $("#research_tools_flp_text").show();
        } else {
            $("#research_tools_flp_text").val('');
            $("#research_tools_flp_text").hide();
        }
    };

    $("#research_tools_flp").bind('keyup change',
    function() {
        toggle_research_tools_flp_text();

    });

    // setup research_tools_other_text
    function toggle_research_tools_other_text() {
        if ($("#research_tools_other").is(':checked')) {
            $("#research_tools_other_text").show();
        } else {
            $("#research_tools_other_text").val('');
            $("#research_tools_other_text").hide();
        }
    };

    $("#research_tools_other").bind('keyup change',
    function() {
        toggle_research_tools_other_text();

    });


    // setup human_condition_more
    function toggle_human_condition_more() {
        if ($("input[name=human_condition]:checked").val() == 'yes') {
            $("#human_condition_more").show();
        } else {
            $("#human_condition_omim").val('');
            $("#human_condition_text").val('');
            $("#human_condition_more").hide();
        }
    };

    $("input[name=human_condition]").bind('keyup change',
    function() {
        toggle_human_condition_more();

    });

    // setup current_backg_text
    function toggle_current_backg_text() {
        if ($("#current_backg").val() == 'other') {
            $("#current_backg_text").show();
        } else {
            $("#current_backg_text").val('');
            $("#current_backg_text").hide();
        }
    };

    $("#current_backg").bind('keyup change',
    function() {
        toggle_current_backg_text();

    });

    // setup mutation_original_backg_other
    function toggle_mutation_original_backg_other($mutation) {
        if ($('.mutation_original_backg select', $mutation).val() == 'other') {
            $('.mutation_original_backg input', $mutation).show();
        } else {
            $('.mutation_original_backg input', $mutation).val('');
            $('.mutation_original_backg input', $mutation).hide();
        }
    };

    $('.mutation_original_backg select').live('keyup change',
    function() {
        var $mutation = $(this).parent().parent().parent();
        toggle_mutation_original_backg_other($mutation);

    });

    // run setup
    function layout() {
        toggle_ip_rights_text();
        toggle_deposited_elsewhere_text();
        toggle_exclusive_owner_text();
        toggle_delayed_release_text();
        toggle_research_areas_other_text();
        toggle_research_tools_other_text();
        toggle_research_tools_cre_text();
        toggle_research_tools_flp_text();
        toggle_homozygous_matings_required_text();
        toggle_human_condition_more();
        toggle_current_backg_text();
    }

    // setup pmfetch
    $("input.autofill_pmid").live('click',
    function() {
        var pmid = $(this).parent().prev().prev().children("input:text").val();
        pmfetch(pmid);
        return false;
    });

    function pmfetch(pmid) {
        if (/^\d+$/.test(pmid)) {
            alert(pmid);
        } else {
            alert(pmid);
        }
    }

    // fill contact info using data from prev steps
    $("#producer_same_as_submitter").click(function() {
        $("#producer_email").val($("#submitter_email").val());
        $("#producer_title").val($("#submitter_title").val());
        $("#producer_firstname").val($("#submitter_firstname").val());
        $("#producer_lastname").val($("#submitter_lastname").val());
        $("#producer_inst").val($("#submitter_inst").val());
        $("#producer_dept").val($("#submitter_dept").val());
        $("#producer_tel").val($("#submitter_tel").val());
        $("#producer_fax").val($("#submitter_fax").val());
        $("#producer_addr_1").val($("#submitter_addr_1").val());
        $("#producer_addr_2").val($("#submitter_addr_2").val());
        $("#producer_city").val($("#submitter_city").val());
        $("#producer_county").val($("#submitter_county").val());
        $("#producer_postcode").val($("#submitter_postcode").val());
        $("#producer_country").val($("#submitter_country").val());
        // $('#next').focus();
        $('#producer_email').focus();
    });

    $("#shipper_same_as_submitter").click(function() {
        $("#shipper_email").val($("#submitter_email").val());
        $("#shipper_title").val($("#submitter_title").val());
        $("#shipper_firstname").val($("#submitter_firstname").val());
        $("#shipper_lastname").val($("#submitter_lastname").val());
        $("#shipper_inst").val($("#submitter_inst").val());
        $("#shipper_dept").val($("#submitter_dept").val());
        $("#shipper_tel").val($("#submitter_tel").val());
        $("#shipper_fax").val($("#submitter_fax").val());
        $("#shipper_addr_1").val($("#submitter_addr_1").val());
        $("#shipper_addr_2").val($("#submitter_addr_2").val());
        $("#shipper_city").val($("#submitter_city").val());
        $("#shipper_county").val($("#submitter_county").val());
        $("#shipper_postcode").val($("#submitter_postcode").val());
        $("#shipper_country").val($("#submitter_country").val());
        // $('#next').focus();
        $('#shipper_email').focus();
    });

    $("#shipper_same_as_producer").click(function() {
        $("#shipper_email").val($("#producer_email").val());
        $("#shipper_title").val($("#producer_title").val());
        $("#shipper_firstname").val($("#producer_firstname").val());
        $("#shipper_lastname").val($("#producer_lastname").val());
        $("#shipper_inst").val($("#producer_inst").val());
        $("#shipper_dept").val($("#producer_dept").val());
        $("#shipper_tel").val($("#producer_tel").val());
        $("#shipper_fax").val($("#producer_fax").val());
        $("#shipper_addr_1").val($("#producer_addr_1").val());
        $("#shipper_addr_2").val($("#producer_addr_2").val());
        $("#shipper_city").val($("#producer_city").val());
        $("#shipper_county").val($("#producer_county").val());
        $("#shipper_postcode").val($("#producer_postcode").val());
        $("#shipper_country").val($("#producer_country").val());
        // $('#next').focus();
        $('#shipper_email').focus();


        // mock check action
        $(".check_reference_pmid", ".check_mutation_gene_sym", ".check_mutation_gene_mgi", ".check_mutation_allele_sym", ".check_mutation_allele_mgi").live('click',
        function() {
            alert("Not yet implemented!");
        });
    });

    // bind wizard progress to persistence
    $("#submission").bind("step_shown",
    function(event, data) {
        if (!data.isBackNavigation) {
            if (data.previousStep == 'start') {
                var mutation_added = false;
                var reference_added = false;
                uid = $.trim($('#submitter_email').val());
                if (uid) {
                    $('#submitter_email_alias').text(uid);
                    var prev_session = $.storage.get(uid);
                    console.warn('prev_session');
                    console.dir(prev_session);
                    if (prev_session) {
                        if (prev_session['complete'] == true) {
                            if (prev_session['submitter'] && confirm("Do you want to fill your contact information with details from your last submission?")) {
                                current_session['submitter'] = $.extend(true, {},
                                prev_session['submitter']);
                            }
                        } else if (prev_session['complete'] == false) {
                            if (confirm("Do you want to continue the submission that you started " + $.easydate.format_date(new Date(prev_session['date_started'])) + " on this computer but never completed?")) {
                                current_session = $.extend(true, {},
                                prev_session);
                                current_session['date_started'] = current_date;
                            } else {
                                if (prev_session['submitter'] && confirm("Do you want to fill your contact information with details from your last submission?")) {
                                    current_session['submitter'] = $.extend(true, {},
                                    prev_session['submitter']);
                                }
                            }
                        }

                        // remove all mutations if in session
                        if (current_session['genotype']) {
                            $("#genotype > fieldset").remove();
                        }

                        // remove all references if in session
                        if (current_session['references']) {
                            $("#references > fieldset").remove();
                        }

                        // restore form from session
                        $.each(current_session,
                        function(key, step) {
                            $.each(step,
                            function(name, value) {

                                // if field is part of mutation, check if mutation exists and if not add it
                                if (name.match(/^mutation_/)) {
                                    var index = parseInt(name.substring(name.length - 1));
                                    if (!$('#mutation_type_' + index).length) {
                                        add_mutation(index, false);
                                    }
                                }
                                // if field is part of reference, check if reference exists and if not add it
                                if (name.match(/^reference_/)) {
                                    var index = parseInt(name.substring(name.length - 1));
                                    if (!$('#reference_descr_' + index).length) {
                                        add_reference(index, false);
                                    }
                                }

                                // get field and fill it
                                var $field = $('[name=' + name + ']');

                                if ($field.is('input:radio') || $field.is('input:checkbox')) {
                                    $field.each(function() {
                                        if ($(this).val() == value) {
                                            $(this).attr('checked', 'checked');
                                        }
                                    });
                                } else {
                                    $field.val(value);
                                    if ($field.attr('name').match(/^mutation_type_/)) {
                                        var $mutation = $field.parent().parent().parent();
                                        layout_mutation($mutation);
                                    }

                                    if ($field.attr('name').match(/^mutation_original_backg_other_/)) {
                                        // toggle mutation original genetic background
                                        toggle_mutation_original_backg_other($mutation);
                                    }
                                }
                            });
                        });
                    }
                }
                layout();
            } else if (!data.isFirstStep) {
                // persist previous step
                var $previousStep = $('#' + data.previousStep);
                current_session[data.previousStep] = {};
                $('input[type!=button], textarea, select', $previousStep).each(function() {
                    if ($(this).is('input:radio') || $(this).is('input:checkbox')) {
                        $(this).each(function() {
                            if ($(this).is(':checked')) {
                                current_session[data.previousStep][$(this).attr('name')] = $(this).val();
                            }
                        });
                    } else {
                        current_session[data.previousStep][$(this).attr('name')] = $.trim($(this).val());
                    }
                });
                if (uid) {
                    $.storage.set(uid, current_session);
                }
            }

            console.warn('current_session');
            console.dir(current_session);
        }

        // build summary page just before submission
        if (data.isLastStep) {
            if (current_session) {
                $.each(current_session,
                function(key, step) {
                    // print heading
                    var $heading = $('#' + key).find('h2');
                    if ($heading) {
                        $('<h3>' + $heading.text().replace(/ \(.*/, '') + '</h3>').appendTo('#summary');
                    }
                    $.each(step,
                    function(name, value) {
                        if (value) {
                            var $field = $(':input[name=' + name + ']');
                            var $label = $('label[for=' + name + ']');
                            if (!$label) {
                                $label = $field.closest('label');
                            }
                            var val;
                            if ($field.is('select')) {
                                val = $field.find(':selected').text();
                            } else if ($field.is(':radio')) {
                                val = $field.filter('[value=' + value + ']').closest('label').text();
                            } else if ($field.is(':checkbox')) {
                                $label = $field.parent().parent().parent().find('label');
                                val = $field.filter('[value=' + value + ']').closest('label').text();
                            } else {
                                val = $field.val();
                            }
                            var label = $label.html();
                            if (label) {
                                label = '<b>' + label + '</b>:&nbsp;';
                            } else {
                                label = '';
                            }
                            $('<p>' + label + val + '</p>').appendTo('#summary');
                        }
                    });
                });
            }
            $('#next').focus();
        }
    });

    $.validator.addMethod("tel",
    function(value, element) {
        return this.optional(element) || /^\+\d+[\d\-\(\)\s]+(x\d+)?$/i.test(value);
    },
    "Please enter a valid phone/fax number (must begin with <b>+</b> followed by the country code, and contain only numbers, hyphens, spaces, or parentheses. An extension must begin with <b>x</b> followed by the extension number).");

    var validation_options = {
        rules: {
            submitter_email: {
                required: true,
                email: true
            },
            submitter_lastname: 'required',
            submitter_tel: {
                required: true,
                tel: true
            },
            submitter_fax: {
                required: true,
                tel: true
            },
            submitter_inst: 'required',
            submitter_addr_1: 'required',
            submitter_city: 'required',
            submitter_postcode: 'required',
            submitter_country: 'required',

            producer_email: {
                required: true,
                email: true
            },
            producer_lastname: 'required',
            producer_tel: {
                required: true,
                tel: true
            },
            producer_fax: {
                required: true,
                tel: true
            },
            producer_inst: 'required',
            producer_addr_1: 'required',
            producer_city: 'required',
            producer_postcode: 'required',
            producer_country: 'required',

            shipper_email: {
                required: true,
                email: true
            },
            shipper_lastname: 'required',
            shipper_tel: {
                required: true,
                tel: true
            },
            shipper_fax: {
                required: true,
                tel: true
            },
            shipper_inst: 'required',
            shipper_addr_1: 'required',
            shipper_city: 'required',
            shipper_postcode: 'required',
            shipper_country: 'required',

            strain_name: 'required',
            genetic_descr: 'required',
            current_backg: 'required',
            current_backg_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }
                }
            },
            homozygous_matings_required_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }
                }
            },

            homozygous_phenotypic_descr: 'required',
            heterozygous_phenotypic_descr: 'required',

            homozygous_viable: 'required',
            heterozygous_fertile: 'required',
            homozygous_fertile: 'required',
            homozygous_matings_required: 'required',
            weaning_age: 'digits',
            immunocompromised: 'required',

            human_condition: 'required',
            human_condition_omim: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },
            human_condition_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

            research_areas_other_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

            research_tools_other_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

            deposited_elsewhere: 'required',
            deposited_elsewhere_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },
            similar_strains: 'required',
            ip_rights: 'required',
            ip_rights_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

            exclusive_owner: 'required',
            exclusive_owner_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

            delayed_release: 'required',
            delayed_release_text: {
                required: {
                    depends: function(element) {
                        return ! $(element).is(':hidden');
                    }

                }
            },

        },
        messages: {
            // producer_email: {
            //     required: "Please specify your email",
            //     email: "Correct format is name@domain.com"
            // },
            // producer_firstname: "Please specify your firstname",
            // producer_lastname: "Please specify your lastname",
            // producer_country: "Please specify your country",
            },
        errorPlacement: function(error, element) {
            if ($(element).is('input:radio') || $(element).is('input:checkbox')) {
                error.appendTo(element.parent().parent().next());
            } else {
                error.appendTo(element.parent().next());
            }
        },
    };

    for (var index = 0; index < 10; index++) {
        validation_options['rules']['mutation_type_' + index] = 'required';
        validation_options['rules']['mutation_subtype_' + index] = {
            'required': {
                depends: function(element) {
                    var name = $(element).attr('name');
                    return $.inArray($("[name=mutation_type_" + name.substring(name.length - 1) + "]").val(), ['CH', 'IN', 'TM']) > -1;
                },
            },
        };
        validation_options['rules']['mutation_original_backg_other_' + index] = {
            'required': {
                depends: function(element) {
                    return ! $(element).is(':hidden');
                },
            },
        };
        validation_options['rules']['mutation_gene_sym_' + index] = {
            'required': {
                depends: function(element) {
                    var name = $(element).attr('name');
                    return $.inArray($("[name=mutation_type_" + name.substring(name.length - 1) + "]").val(), ['CH', 'GT', 'IN', 'SP', 'TM', 'XX']) > -1;
                },
            },
        };
        validation_options['rules']['mutation_gene_mgi_' + index] = {
            'required': {
                depends: function(element) {
                    var name = $(element).attr('name');
                    return $.inArray($("[name=mutation_type_" + name.substring(name.length - 1) + "]").val(), ['CH', 'GT', 'IN', 'SP', 'TM', 'XX']) > -1;
                },
            },
        };
        validation_options['rules']['reference_descr_' + index] = 'required';
        validation_options['rules']['reference_journal_' + index] = 'required';
        validation_options['rules']['reference_year_' + index] = 'required';
        validation_options['rules']['reference_pages_' + index] = 'required';
    }

    // setup form wizard plugin
    $('#submission').formwizard({
        formPluginEnabled: true,
        validationEnabled: true,
        historyEnabled: true,
        focusFirstInput: true,
        disableUIStyles: true,
        // disableInputFields: false,
        formOptions: {
            success: function(data, $form, options) {
                // if (uid) {
                //     current_session['complete'] = true;
                //     $.storage.set(uid, current_session);
                // }
                },
            beforeSubmit: function(data, $form, options) {
                if (uid) {
                    current_session['complete'] = true;
                    $.storage.set(uid, current_session);
                }
                alert("success");
                window.location.reload(true);
            },
            clearForm: true,
            resetForm: false,
        },
        validationOptions: validation_options,
    });
});
