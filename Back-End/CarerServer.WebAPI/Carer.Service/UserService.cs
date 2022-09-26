using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Repository.Common;
using Carer.Service.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Service
{
    class UserService : IUserService
    {
        private IUserRepository userRepository;
        private IPetService petService;
        private IAdRepository adRepository;


        public UserService(IUserRepository userRepository,IPetService petService, IAdRepository adRepository)
        {
            this.userRepository = userRepository;
            this.petService = petService;
            this.adRepository = adRepository;
        }

        public async Task<List<UserDomainModel>> GetAllUserAsync()
        {
            return await userRepository.GetAllUsersAsync();
        }

        public async Task<UserDomainModel> GetUserByIdAsync(Guid id)
        {
            UserDomainModel user = await userRepository.GetUserByIdAsync(id);
            user.Pets = await petService.GetAllPetAsync(new PetFilter { OwnerId = user.Id });
            return user;
        }

        public async Task PostUserAsync(UserDomainModel user)
        {
            await userRepository.PostUserAsync(user);
        }

        public async Task PutUserAsync(UserDomainModel user)
        {
            await userRepository.PutUserAsync(user);
        }

        public async Task DeleteUserAsync(Guid id)
        {
            await userRepository.DeleteUserAsync(id);
        }

        public async Task AddCarerReview(CarerReviewDomainModel review)
        {
            userRepository.AddCarerReview(review);

            int status = adRepository.GetAdStatusAsync(review.Id);

            if ((review.IsPoster == true && status == 3) || (review.IsPoster == false && status == 2))
            {
                adRepository.SetStatusAdAsync(review.Id, 4);
            }
            if(status == 1)
            {
                if (review.IsPoster)
                {
                    adRepository.SetStatusAdAsync(review.Id, 2);
                }
                else
                {
                    adRepository.SetStatusAdAsync(review.Id, 3);
                }
            }
        }

        public async Task AddOwnerReview(OwnerReviewDomainModel review)
        {
            userRepository.AddOwnerReview(review);

            int status = adRepository.GetAdStatusAsync(review.Id);

            if ((review.IsPoster == true && status == 3) || (review.IsPoster == false && status == 2))
            {
                adRepository.SetStatusAdAsync(review.Id, 4);
            }
            if (status == 1)
            {
                if (review.IsPoster)
                {
                    adRepository.SetStatusAdAsync(review.Id, 2);
                }
                else
                {
                    adRepository.SetStatusAdAsync(review.Id, 3);
                }
            }
        }


    }
}
