files = {'DataLogGabe.log','DataLogAlex.log','DataLogMarie.log','DataLogDylan.log','DataLogJoe.log'};

for file = files
    xmlData = parseXML(file{1});
    xmlData = xmlData(2); % first element is useless
    xmlData.Children = xmlData.Children(2:2:end); % every other entry is useless
    % Comb through training trials
    word_guess_times = containers.Map;
    training_trials_times = [];
    most_recent_log_index = 2;
    for trial = 1:10
        words_guessed_start_times = [];
        for n = most_recent_log_index:numel(xmlData.Children)
            if strcmp(xmlData.Children(n).Children(18).Children.Data,'new word from training data') % 18 = message
                start_time = str2num(xmlData.Children(n).Children(4).Children.Data); % 4 = millisecond time
                start_index = n;
                words_guessed_start_times = [words_guessed_start_times start_time];
                break
            end
        end
        for n = start_index+1:numel(xmlData.Children)
            if strcmp(xmlData.Children(n).Children(18).Children.Data,'guessed a word and was wrong')
                words_guessed_start_times = [words_guessed_start_times start_time];
            elseif strcmp(xmlData.Children(n).Children(18).Children.Data,'guessed a word and was right')
                end_time = str2num(xmlData.Children(n).Children(4).Children.Data);
                trial_time = end_time - start_time;
                training_trials_times = [training_trials_times trial_time];
                most_recent_log_index = n+1;
                for n = 1:numel(words_guessed_start_times)
                    if not(isKey(word_guess_times,n-1))
                        word_guess_times(num2str(n-1)) = [];
                    end
                    word_guess_times(num2str(n-1)) = [word_guess_times(num2str(n-1)) end_time-words_guessed_start_times(n)];
                end
                break
            elseif strcmp(xmlData.Children(n).Children(18).Children.Data,'skipped a word in training data and was wrong')
                most_recent_log_index = n+1;
                words_guessed_start_times = [];
                break
            end
        end
    end
    % Comb through testing trials
    testing_trial_times_until_unsolvable = [];
    for trial = 1:10
        for n = most_recent_log_index:numel(xmlData.Children)
            if strcmp(xmlData.Children(n).Children(18).Children.Data,'new word from testing data')
                start_time = str2num(xmlData.Children(n).Children(4).Children.Data);
                start_index = n;
                break
            end
        end
        for n = start_index+1:numel(xmlData.Children)
            if strcmp(xmlData.Children(n).Children(18).Children.Data,'marked a word in testing data as unsolvable and was wrong') | ...
                    strcmp(xmlData.Children(n).Children(18).Children.Data,'marked a word in testing data as unsolvable and was right')
                end_time = str2num(xmlData.Children(n).Children(4).Children.Data);
                time_until_unsolvable = end_time - start_time;
                testing_trial_times_until_unsolvable = [testing_trial_times_until_unsolvable time_until_unsolvable];
                most_recent_log_index = n+1;
                break
            elseif strcmp(xmlData.Children(n).Children(18).Children.Data,'guessed a word and was right')
                most_recent_log_index = n+1;
                break
            end
        end
    end
end